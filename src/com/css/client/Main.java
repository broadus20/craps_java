package com.css.client;

import com.craps.app.CrapsApp;

import java.io.IOException;

//we need to change this name to Main/CrapsAppClient class (defer this for later)
class Main {

    public static void main(String[] args) throws IOException {
        CrapsApp app = new CrapsApp();
        app.execute();
    }
}

