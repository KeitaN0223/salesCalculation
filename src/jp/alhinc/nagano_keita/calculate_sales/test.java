package jp.alhinc.nagano_keita.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class test{
	public static void main(String[] args){

		//支店のマップを宣言
		HashMap<String, String> branchMap = new HashMap<String, String>();

		//商品定義のマップを宣言
		HashMap<String, String> commodityMap = new HashMap<String, String>();

		// 1.1 売上集計フォルダから支店定義ファイルがあるか参照する
		File branchFile = new File(args[0] , "branch.lst");

		//2.1 商品定義ファイルがあるか参照する
		File commodityFile = new File(args[0], "commodity.lst");

		//3.1 rcdファイルがあるか参照する ファイル名を出力した
		File dir = new File(args[0]);
		File[] files = dir.listFiles();
		for (int i = 0; i <files.length; i++){
			File file = files[i];
			System.out.println(file);
		}

		//1.1 売上集計フォルダから支店定義ファイルがあるか参照する
		if(!branchFile.exists()){
			System.out.println("支店定義ファイルが存在しません");
			return;
		}

		//2.1 商品定義ファイルがあるか参照する
		if(!commodityFile.exists()){
			System.out.println("商品定義ファイルが存在しません");
			return;
		}

		//1.2 支店定義ファイルのフォーマットが正しいのか
		try{
			FileReader fr;
			fr = new FileReader(branchFile);
			BufferedReader br = new BufferedReader(fr);
			String s ;

			//2.2 商品定義ファイルのフォーマットが正しいのか
			FileReader fr1;
			fr1 = new FileReader(commodityFile);
			BufferedReader br1 = new BufferedReader(fr1);
			String s1 ;

			//1.2 ファイルの読み込み
			while((s = br.readLine()) != null){
				String[] resultArray = s.split(",");
				//1.2 支店番号が不正のとき、エラーメッセージを送る
				if(resultArray[0].length() != 3){
					System.out.println("支店定義ファイルのフォーマットが不正です");
					return;
				}
				//1.2 支店番号と支店名を紐付けた
				branchMap.put(resultArray[0], resultArray[1]);
			}

			//2.2 商品定義ファイルのフォーマットが正しいのか
			while((s1 = br1.readLine()) != null){
				String[] resultArray1 = s1.split(",");
				if(resultArray1[0].length() != 8){
					System.out.println("商品定義ファイルのフォーマットが不正です");
					return;
				}
				//2.2 商品番号と商品を紐付けた
				commodityMap.put(resultArray1[0], resultArray1[1]);

			}

			//お試し出力
			System.out.println(branchMap.get("003"));
			System.out.println(commodityMap.get("SFT00002"));
			br.close();
			br1.close();
		}catch(IOException e){
			System.out.println("IOException型の例外をキャッチしました");
			System.out.println(e);
		}


	}
}
