package com.lc.Block;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lc.Util.RocksDBUtils;

import lombok.Getter;

public class Blockchain {

    @Getter
    private String lastBlockHash;

    private Blockchain(String lastBlockHash) {
        this.lastBlockHash = lastBlockHash;
    }
	
	/**
	 * 添加区块
	 * @param block 区块
	 * @throws Exception 
	 */
	public void addBlock(String data) throws Exception {
		String lastBlockHash = RocksDBUtils.getInstance().getLastBlockHash();
		if(StringUtils.isBlank(lastBlockHash)) {
			throw new Exception("Fail to add block into blockchain ! ");
		}
		this.addBlock(Block.newBlock(lastBlockHash, data));
	}
	
	public void addBlock(Block block) {
		RocksDBUtils.getInstance().putLastBlockHash(lastBlockHash);
		RocksDBUtils.getInstance().putBlock(block);
		this.lastBlockHash = block.getHash();
	}

	/**
	 * 创建区块链
	 * @return
	 */
	public static Blockchain newBlockchain() {
		String lastBlockHash = RocksDBUtils.getInstance().getLastBlockHash();
		if(StringUtils.isBlank(lastBlockHash)) {
			Block genesisBlock = Block.newGenesisBlock();
			lastBlockHash = genesisBlock.getHash();
			RocksDBUtils.getInstance().putBlock(genesisBlock);
			RocksDBUtils.getInstance().putLastBlockHash(lastBlockHash);
		}
		return new Blockchain(lastBlockHash);
	}


	/**
     * 区块链迭代器
     */
    public class BlockchainIterator {

        private String currentBlockHash;

        public BlockchainIterator(String currentBlockHash) {
            this.currentBlockHash = currentBlockHash;
        }

        /**
         * 是否有下一个区块
         *
         * @return
         */
        public boolean hashNext() {
            if (StringUtils.isBlank(currentBlockHash)) {
                return false;
            }
            Block lastBlock = RocksDBUtils.getInstance().getBlock(currentBlockHash);
            if (lastBlock == null) {
                return false;
            }
            // 创世区块直接放行
            if (lastBlock.getPreviousHash().length() == 0) {
                return true;
            }
            return RocksDBUtils.getInstance().getBlock(lastBlock.getPreviousHash()) != null;
        }


        /**
         * 返回区块
         *
         * @return
         */
        public Block next() {
            Block currentBlock = RocksDBUtils.getInstance().getBlock(currentBlockHash);
            if (currentBlock != null) {
                this.currentBlockHash = currentBlock.getPreviousHash();
                return currentBlock;
            }
            return null;
        }
    }

    public BlockchainIterator getBlockchainIterator() {
        return new BlockchainIterator(lastBlockHash);
    }

}
