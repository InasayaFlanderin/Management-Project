package com.myteam.work;

import com.myteam.work.gui.Window;
import com.myteam.work.gui.pages.LoginPage;

public class App {
    public static void main(String[] args) {
		Window.getWindow().switchPage(LoginPage.getPage());
    }
}
