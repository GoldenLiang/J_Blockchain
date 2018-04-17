package com.lc.Block;

import java.math.BigInteger;
import java.time.Instant;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.lc.Util.ByteUtils;

/**
 * @author lc
 * 区块
 */
public class Block {

	/**
	 * hash 值
	 */
	private String hash;
	
	/**
	 * 区块数据
	 */
	private String data;
	
	/**
	 * 前一个区块的 hash
	 */
	private String previousHash;
	
	/**
	 * 区块创建时间(单位：秒)
	 */
	private long timeStamp;
	
	public Block() {}

	public Block(String hash, String data, String previousHash, long timeStamp) {
		this();
		this.hash = hash;
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = timeStamp;
	}

	/**
	 * 创建创世区块
	 * @return
	 */
	public static Block newGenesisBlock() {
		return Block.newBlock("", "Genesis Block");
	}

	/**
	 * 创建新区块
	 * @param previousHash
	 * @param data
	 * @return
	 */
	public static Block newBlock(String previousHash, String data) {
		Block block = new Block("",  data, previousHash, Instant.now().getEpochSecond());
		block.setHash();
		return block;
	}

	/**
	 * 计算区块 Hash
	 */
	private void setHash() {
		byte[] prevBlockHashBytes = {};
		if(StringUtils.isNoneBlank(this.getPreviousHash())) {
			prevBlockHashBytes = new BigInteger(this.getPreviousHash(), 16).toByteArray();
		}
		
		byte[] headers = ByteUtils.merge(
				prevBlockHashBytes,
				this.getData().getBytes(),
				ByteUtils.toBytes(this.getTimeStamp()));
		
		this.setHash(DigestUtils.sha256Hex(headers));
	}

	public String getHash() {
		return hash;
	}

	public String getData() {
		return data;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
