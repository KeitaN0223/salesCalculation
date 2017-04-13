package jp.alhinc.nagano_keita.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class process {
	public static void main(String[] args) {

		// 支店のマップを宣言
		HashMap<String, String> branchMap = new HashMap<String, String>();

		// 商品定義のマップを宣言
		HashMap<String, String> commodityMap = new HashMap<String, String>();

		//支店ごとの売上マップを宣言する
		HashMap<String, Integer> branchEarnings = new HashMap<String, Integer>();

		//商品ごとの売上マップを宣言する
		HashMap<String, Integer> itemEarnings = new HashMap<String, Integer>();

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
				if (resultArray[0].length() != 3) {
					System.out.println("支店定義ファイルのフォーマットが不正です");
					return;
				}
				// 1.2 支店番号と支店名を紐付けた
				branchMap.put(resultArray[0], resultArray[1]);

			}
			br.close();

		} catch (IOException e) {
			System.out.println("IOException型の例外をキャッチしました");
			System.out.println(e);
		} finally {
		}

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
				if (resultArray1[0].length() != 8) {
					System.out.println("商品定義ファイルのフォーマットが不正です");
					return;
				}
				// 2.2 商品番号と商品を紐付けた
				commodityMap.put(resultArray1[0], resultArray1[1]);

			}
			br1.close();
		} catch (IOException e) {
			System.out.println("IOException型の例外をキャッチしました");
			System.out.println(e);
		} finally {
		}

		// 3.1 ファイル名半角数字8桁のrcdファイルがあるか参照する
		File dir = new File(args[0]);
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			String fileName = file.getName();
			if (fileName.matches("^[0-9]{8}.rcd$")) {

				// 3.1 rcdファイルをrcdFilesに格納した
				String rcdFiles = new String(fileName);
				System.out.println(rcdFiles);

				// 3.2 rcdファイルを読み込み集計する

				for (int n = 0; n < rcdFiles.length(); n++)
					try {
						FileReader fr2;
						fr2 = new FileReader(file);
						BufferedReader br2 = new BufferedReader(fr2);

						for(int t = 0 ; t < 3 ; t++){
							String branchNum = br2.readLine();
							String itemNum = br2.readLine();
							String earnings = br2.readLine();
							System.out.println(branchNum);

						}
					}catch (IOException e) {
						System.out.println("IOException型の例外をキャッチしました");
						System.out.println(e);
					}
			}
		}
	}
}
