package com.lite.blackdream.framework.util;

/**
 * @author LaineyC
 * JavaScript整数最大支持53位
 * 时间41位 机器号1位 毫秒ID自增6位 一共48位
 */
public class IdWorker {

    /**
     * 2016-01-01 00:00:00
     */
    private final static long twepoch = 1451577600000L;

    /**
     * 机器标识位数
     */
    private final static long workerIdBits = 1L;

    /**
     * 机器ID支持机器节点数0~1
     */
    public final static long maxWorkerId = ~(-1L << workerIdBits);

    /**
     * 毫秒内自增位
     */
    private final static long sequenceBits = 6L;

    /**
     * 毫秒内sequence范围0~63
     */
    public final static long sequenceMask = ~(-1L << sequenceBits);

    /**
     * 机器ID偏左移6位
     */
    private final static long workerIdShift = sequenceBits;

    /**
     * 时间毫秒左移7位
     */
    private final static long timestampLeftShift = sequenceBits + workerIdBits;

    private final long workerId;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    public IdWorker(final long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
        }
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        }
        else {
            this.sequence = 0;
        }
        this.lastTimestamp = timestamp;
        return ((timestamp - twepoch << timestampLeftShift)) | (this.workerId << workerIdShift) | (this.sequence);
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

