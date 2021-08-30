/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Available apps in SiPAS-tools
 */
public enum AppNames implements Comparable <AppNames> {

    /**
     * Screening the variables
     */
    ShowColl ("ShowColl"),

    /**
     * Screening the variables of the collection
     */
    ShowVars ("ShowVars"),

    /**
     * Query the document
     */
    Query ("Query"),

    /**
     * Download the update table
     */
    UpdateTable ("UpdateTable"),

    /**
     * Download the update table
     */
    DownTable ("DownTable"),

    /**
     * Download the whole database
     */
    Download ("Download"),

    /**
     * Select
     */
    Select ("Select");

    public final String name;

    AppNames(String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }
}
