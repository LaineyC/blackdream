package com.lite.blackdream.framework.util;

/**
 * @author LaineyC
 */
public class IdWorker {

    private final long workerId;

    private final static long twepoch = 1288834974657L;

    private long sequence = 0L;

    /**
     * 机器标识位数
     */
    private final static long workerIdBits = 4L;

    /**
     * 机器ID最大值
     */
    public final static long maxWorkerId = -1L ^ -1L << workerIdBits;

    /**
     * 毫秒内自增位
     */
    private final static long sequenceBits = 10L;

    /**
     * 机器ID偏左移12位
     */
    private final static long workerIdShift = sequenceBits;

    /**
     * 时间毫秒左移22位
     */
    private final static long timestampLeftShift = sequenceBits + workerIdBits;

    public final static long sequenceMask = -1L ^ -1L << sequenceBits;

    private long lastTimestamp = -1L;

    public IdWorker(final long workerId) {
        if (workerId > this.maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & this.sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        }
        else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
        }
        this.lastTimestamp = timestamp;
        long nextId = ((timestamp - twepoch << timestampLeftShift)) | (this.workerId << this.workerIdShift) | (this.sequence);
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

}

