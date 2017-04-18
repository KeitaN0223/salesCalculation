package jp.alhinc.nagano_keita.calculate_sales;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class process {
	public static void main(String[] args) {
		if(args.length != 1){
			System.out.println("予期せぬエラーが発生しました");
		}
		// 支店のマップを宣言
		HashMap<String, String> branchMap = new HashMap<String, String>();

		// 商品定義のマップを宣言
		HashMap<String, String> commodityMap = new HashMap<String, String>();

		// 支店ごとの売上マップを宣言する
		HashMap<String, Integer> branchEarningsMap = new HashMap<String, Integer>();

		// 商品ごとの売上マップを宣言する
		HashMap<String, Integer> commodityEarningsMap = new HashMap<String, Integer>();
		try {
			// 1.1 売上集計フォルダから支店定義ファイルがあるか参照する
			File branchFile = new File(args[0], "branch.lst");

			// 1.1 売上集計フォルダから支店定義ファイルがあるか参照する
			if (!branchFile.exists()) {
				System.out.println("支店定義ファイルが存在しません");
				return;
			}

			// 1.2 支店定義ファイルのフォーマットが正しいのか
			try {
				FileReader fr;
				fr = new FileReader(branchFile);
				BufferedReader br = new BufferedReader(fr);
				String s;
				// 1.2 ファイルの読み込み
				while ((s = br.readLine()) != null) {
					String[] resultArray = s.split(",");
					// 1.2 支店番号が不正のとき、エラーメッセージを送る
					if (resultArray.length != 2){
						System.out.println("支店定義ファイルのフォーマットが不正です");
						return;
					}
					if (!resultArray[0].matches("^[0-9]{3}$") ) {
						System.out.println("支店定義ファイルのフォーマットが不正です");
						return;
					}
					// 1.2 支店番号と支店名を紐付けた
					branchMap.put(resultArray[0], resultArray[1]);
					// 支店番号に紐づいている支店別売上を0円に初期化
					branchEarningsMap.put(resultArray[0], 0);
				}

			} catch (IOException e) {
				System.out.println("IOException型の例外をキャッチしました");
				System.out.println(e);
			} finally {
			}

			// System.out.println(branchEarningsMap.entrySet());

			// 2.1 商品定義ファイルがあるか参照する
			File commodityFile = new File(args[0], "commodity.lst");

			// 2.1 商品定義ファイルがあるか参照する
			if (!commodityFile.exists()) {
				System.out.println("商品定義ファイルが存在しません");
				return;
			}

			try {
				// 2.2 商品定義ファイルのフォーマットが正しいのか
				FileReader fr1;
				fr1 = new FileReader(commodityFile);
				BufferedReader br1 = new BufferedReader(fr1);
				String s1;

				// 2.2 商品定義ファイルのフォーマットが正しいのか
				while ((s1 = br1.readLine()) != null) {
					String[] resultArray1 = s1.split(",");
					if (resultArray1.length != 2){
						System.out.println("商品定義ファイルのフォーマットが不正です");
					}
					if (!resultArray1[0].matches("^[a-z0-9A-Z]{8}$")) {
						System.out.println("商品定義ファイルのフォーマットが不正です");
						return;
					}
					// 2.2 商品番号と商品を紐付けた
					commodityMap.put(resultArray1[0], resultArray1[1]);
					// 商品番号に紐づいている商品別売上を0円に初期化
					commodityEarningsMap.put(resultArray1[0], 0);
				}

			} catch (IOException e) {
				System.out.println("IOException型の例外をキャッチしました");
				System.out.println(e);
			} finally {
			}

			// 3.1 ファイル名半角数字8桁のrcdファイルがあるか参照する
			File dir = new File(args[0]);
			File[] files = dir.listFiles();
			ArrayList<String> rcdFiles = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				String fileName = file.getName();
				if (fileName.matches("^[0-9]{8}.rcd$")) {
					// 3.1 rcdファイルをrcdFilesに格納した
					rcdFiles.add(fileName);
					;
				}
			}

			try {
				for (int i = 0; i < rcdFiles.size(); i++) {
					// ArrayListでrcdDataをまとめる
					ArrayList<String> rcdData = new ArrayList<String>();
					// System.out.println(rcdFiles.get(i));

					File rcdFilesPath = new File(args[0], rcdFiles.get(i));
					BufferedReader br2 = new BufferedReader(new FileReader(rcdFilesPath));
					String str;
					// 行ごとのデータをArrayListに格納した
					while ((str = br2.readLine()) != null) {
						rcdData.add(str);
					}
					br2.close();
					// System.out.println(rcdData);

					// rcdDataの一番目の要素（支店コード）から売上を呼び出す

					// branchBaseMoneyはもとのお金
					int branchBaseMoney = branchEarningsMap.get(rcdData.get(0));
					// moneyはrcdData三番目の要素（売上）
					int money = Integer.parseInt(rcdData.get(2));
					// branchSumは合計額
					int branchSum = branchBaseMoney + money;

					branchEarningsMap.put(rcdData.get(0), branchSum);

					// rcdDataの二番目の要素（商品コード）から売上を呼び出す
					// commodityBaseMoneyはもとのお金
					int commodityBaseMoney = commodityEarningsMap.get(rcdData.get(1));
					// moneyは先ほどのものを使いまわす
					// commoditySumは合計額
					int commoditySum = commodityBaseMoney + money;
					commodityEarningsMap.put(rcdData.get(1), commoditySum);
				}

				System.out.println(  branchEarningsMap.get("00" + 1));
				// 4 Mapのvalue値でソートしてファイルに出力 要復習
				List<Map.Entry<String, Integer>> branchEntries = new ArrayList<Map.Entry<String, Integer>>(
						branchEarningsMap.entrySet());
				Collections.sort(branchEntries, new Comparator<Map.Entry<String, Integer>>() {
					@Override
					public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
						return ((Integer) entry2.getValue()).compareTo((Integer) entry1.getValue());
					}
				});
				File branchOutFile = new File(args[0], "branch.out");
				FileWriter fw = new FileWriter(branchOutFile);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				for (Entry<String, Integer> s : branchEntries) {
					pw.println(s.getKey() + "," + branchMap.get(s.getKey()) + "," + Integer.toString(s.getValue()));
				}
				pw.close();

				// 4 ソートしてcommodity.outの出力
				List<Map.Entry<String, Integer>> commodityEntries = new ArrayList<Map.Entry<String, Integer>>(
						commodityEarningsMap.entrySet());
				Collections.sort(commodityEntries, new Comparator<Map.Entry<String, Integer>>() {
					@Override
					public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
						return ((Integer) entry2.getValue()).compareTo((Integer) entry1.getValue());
					}
				});
				File commodityOutFile = new File(args[0], "commodity.out");
				FileWriter cfw = new FileWriter(commodityOutFile);
				BufferedWriter cbw = new BufferedWriter(cfw);
				PrintWriter cpw = new PrintWriter(cbw);
				for (Entry<String, Integer> s : commodityEntries) {
					cpw.println(s.getKey() + "," + commodityMap.get(s.getKey()) + "," + Integer.toString(s.getValue()));
				}
				cpw.close();
			} catch (IOException e) {
				System.out.println("IOException型の例外が発生しました");
				System.out.println(e);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("予期せぬエラーが発生しました");
		}
	}

}
