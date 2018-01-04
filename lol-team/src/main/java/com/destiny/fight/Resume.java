package com.destiny.fight;

import java.util.ArrayList;
import java.util.List;

public class Resume {

	private List<String> textList;

	public Resume() {
		textList = new ArrayList<>();
	}

	public void addText(String text) {
		textList.add(text);
	}

	public String readResume() {
		StringBuilder sb = new StringBuilder();
		for (String text : textList) {
			sb.append(text).append("\n");
		}
		return sb.toString();
	}
}
