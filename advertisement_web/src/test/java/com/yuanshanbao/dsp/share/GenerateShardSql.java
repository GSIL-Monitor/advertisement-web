package com.yuanshanbao.dsp.share;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class GenerateShardSql {

	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				GenerateShardSql.class.getResourceAsStream("union.sql")));
		PrintWriter output = new PrintWriter(new File("E:/other/output.sql"));
		String template = "";
		String line = null;
		while ((line = reader.readLine()) != null) {
			template += line + "\n";
		}
		for (int i = 0; i < 100; i++) {
			output.print(String.format(template, i + ""));
		}
		output.close();
		reader.close();
	}

}
