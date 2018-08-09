package com.chnghx.web.web3j.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.List;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.store.UnreadableWalletException;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.NonNull;

@RequestMapping(value = "wallet")
@RestController
public class Web3jController {

	private static final String PROVIDER = "BC";

	/**
	 * 
	 * 测试OK
	 * 
	 * @param pwd
	 * @return
	 * @throws CipherException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	@RequestMapping(value = "simple")
	public WalletFile walletFile(@NonNull String pwd) throws CipherException, NoSuchAlgorithmException, NoSuchProviderException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("EC", PROVIDER);
		keyPairGen.initialize(256, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		ECKeyPair ecKeyPair = ECKeyPair.create(keyPair);

		// String key = "5KZUL62YT34H4sZ15VjGF7gamHFkyjEN7aSbQ5jcR3TUntmLwyw";
		int n = 1024;
		int p = 1;
		WalletFile walletFile = Wallet.create(pwd, ecKeyPair, n, p);
		//
		System.out.println(walletFile);

		// String walletFileStr =
		// "{\"crypto\":{\"cipher\":\"aes-128-ctr\",\"cipherparams\":{\"iv\":\"585288d36e7777b95939aca16be90edf\"},\"ciphertext\":\"9fc0b30d1d942b557b8bb94e6bacac2267\",\"kdf\":\"scrypt\",\"kdfparams\":{\"n\":1024,\"r\":8,\"p\":1,\"dklen\":32,\"salt\":\"3dfa61324efee7ad7c3c32c6b13ca2b23172323af0ed9ddab0193fadcfad5581\"},\"mac\":\"4d2e6745254bed61cadf0c0edb8f10287a4e024271a1f595ddb948852cb94f95\"},\"id\":\"34f1e29c-c69c-4857-afb8-d2d4051811bc\",\"version\":3}";
		// String walletFileStr =
		// "{'crypto':{'cipher':'aes-128-ctr','cipherparams':{'iv':'585288d36e7777b95939aca16be90edf'},'ciphertext':'9fc0b30d1d942b557b8bb94e6bacac2267','kdf':'scrypt','kdfparams':{'n':1024,'r':8,'p':1,'dklen':32,'salt':'3dfa61324efee7ad7c3c32c6b13ca2b23172323af0ed9ddab0193fadcfad5581'},'mac':'4d2e6745254bed61cadf0c0edb8f10287a4e024271a1f595ddb948852cb94f95'},'id':'34f1e29c-c69c-4857-afb8-d2d4051811bc','version':3}";
		// MyWalletFile walletFile =
		// com.alibaba.fastjson.JSONObject.parseObject(walletFileStr,
		// MyWalletFile.class);
		//
		// JSONObject walletFileJson = JSONObject.parseObject(walletFileStr);
		// JSONObject cryptoJSON = walletFileJson.getJSONObject("crypto");
		// JSONObject kdfparamsJSON = cryptoJSON.getJSONObject("kdfparams");
		// MyWalletFile.ScryptKdfParams skp =
		// JSONObject.parseObject(kdfparamsJSON.toJSONString(), ScryptKdfParams.class);
		//
		// walletFile.getCrypto().setKdfparams(skp);

		ECKeyPair op = Wallet.decrypt(pwd, walletFile);

		op.getPrivateKey();
		return walletFile;
	}

	/**
	 * 
	 * 测试OK
	 * 
	 * @param pwd
	 * @return
	 * @throws UnreadableWalletException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws CipherException
	 * @throws IOException 
	 */
	@RequestMapping(value = "exportKeyStore")
	public JSONObject exportKeyStore(@NonNull String pwd)
			throws UnreadableWalletException, NoSuchAlgorithmException, NoSuchProviderException, CipherException, IOException {
		// Security.addProvider(new
		// org.bouncycastle.jce.provider.BouncyCastleProvider());
		// KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("EC", PROVIDER);
		// keyPairGen.initialize(256, new SecureRandom());
		// // keyPairGen.initialize(1024);//生成大小 1024
		// KeyPair keyPair = keyPairGen.generateKeyPair();
		//
		// ECKeyPair ecKeyPair = ECKeyPair.create(keyPair);

		String passphrase = pwd;
		SecureRandom secureRandom = new SecureRandom();
		long creationTimeSeconds = System.currentTimeMillis() / 1000;

		// 随机助记词
		DeterministicSeed deterministicSeed = new DeterministicSeed(secureRandom, 128, passphrase, creationTimeSeconds);
		// 助记词列表
		List<String> mnemonicCode = deterministicSeed.getMnemonicCode();
		// 助记词
		String seedCode = String.join(" ", mnemonicCode);

		DeterministicSeed seed = new DeterministicSeed(seedCode, null, pwd, System.currentTimeMillis());
		DeterministicKeyChain chain = new MyDeterministicKeyChain(seed);
		List<ChildNumber> keyPath = HDUtils.parsePath("M/44H/60H/0H/0/0");
		DeterministicKey key = chain.getKeyByPath(keyPath, true);
		BigInteger privKey = key.getPrivKey();
		System.out.println("私钥：" + privKey);
		//
		// Web3j
		// Credentials credentials = Credentials.create(privKey.toString(16));
		// String address = credentials.getAddress();
		// String privateKey = privKey.toString(16);
		// System.out.println(address);
		// System.out.println("16进制私钥：" + privateKey);

		// //privKey:292fc4056acb3857458a4b3012d856f46c0cf74507341eb793e211f72b9a2f64
		// ECKeyPair ecKeyPair =
		ECKeyPair ecKeyPair = ECKeyPair.create(privKey);
		// System.out.println("私钥：" + ecKeyPair.getPrivateKey());
		//
		// //根据公钥 私钥 密码 得到 keystore(walletFile即为keystore)
		WalletFile walletFile = Wallet.create(pwd, ecKeyPair, 1024, 1);
		//
		// //钱包导入
		// //私钥导入
		// String mPrivateKey = privateKey;
		// ECKeyPair.create(new BigInteger(mPrivateKey,16));
		//
		// //助记词导入,通过助记词得到种子 然后再得到公私钥
		//
		//
		// Keystore导入, 调用web3j中提供的方法
		ECKeyPair ecp = Wallet.decrypt(pwd, walletFile);
		System.out.println("" + ecp.getPrivateKey());
		// System.out.println("" + ecp.getPublicKey());
		
		FileWriter writer = new FileWriter("F://keystore.txt");
		writer.write(JSON.toJSONString(walletFile));
		writer.close();
		
		JSONObject result = new JSONObject();
		result.put("walletFile", walletFile);
		result.put("seedCode", seedCode);
		return result;
	}
	
	/**
	 * 
	 * 测试OK
	 * 
	 * 通过助记词得到种子 然后再得到公私钥
	 * 
	 * @param pwd
	 * @param seedCode
	 * @return
	 * @throws UnreadableWalletException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws CipherException
	 * @throws IOException 
	 */
	@RequestMapping(value = "importSeedCode")
	public JSONObject importSeedCode(@NonNull String pwd, String seedCode)
			throws UnreadableWalletException, NoSuchAlgorithmException, NoSuchProviderException, CipherException, IOException {
		
		//通过助记词得到种子
		DeterministicSeed seed = new DeterministicSeed(seedCode, null, pwd, System.currentTimeMillis());
		DeterministicKeyChain chain = new MyDeterministicKeyChain(seed);
		List<ChildNumber> keyPath = HDUtils.parsePath("M/44H/60H/0H/0/0");
		//得到公私钥
		DeterministicKey key = chain.getKeyByPath(keyPath, true);
		BigInteger privKey = key.getPrivKey();
		System.out.println("私钥：" + privKey);

		ECKeyPair ecKeyPair = ECKeyPair.create(privKey);
		// //根据公钥 私钥 密码 得到 keystore(walletFile即为keystore)
		WalletFile walletFile = Wallet.create(pwd, ecKeyPair, 1024, 1);
		
		// Keystore导入, 调用web3j中提供的方法
		ECKeyPair ecp = Wallet.decrypt(pwd, walletFile);
		System.out.println("" + ecp.getPrivateKey());
		// System.out.println("" + ecp.getPublicKey());
		
		FileWriter writer = new FileWriter("F://keystore.txt");
		writer.write(JSON.toJSONString(walletFile));
		writer.close();
		
		JSONObject result = new JSONObject();
		result.put("walletFile", walletFile);
		result.put("seedCode", seedCode);
		return result;
	}
	
	/**
	 * 
	 * 待测试
	 * 
	 * @param pwd
	 * @param keystore
	 * @return
	 * @throws UnreadableWalletException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws CipherException
	 */
	@RequestMapping(value = "importKeyStore")
	public String importKeyStore(@NonNull String pwd, String keystore)
			throws UnreadableWalletException, NoSuchAlgorithmException, NoSuchProviderException, CipherException {
		//内部参数
		WalletFile walletFile = JSONObject.parseObject(keystore, WalletFile.class);
		ECKeyPair ecp = Wallet.decrypt(pwd, walletFile);
		System.out.println("" + ecp.getPrivateKey());
		
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("walletFile", walletFile);
		return JSON.toJSONString(walletFile);
	}

}
