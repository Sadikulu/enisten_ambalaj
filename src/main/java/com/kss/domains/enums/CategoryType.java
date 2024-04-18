package com.kss.domains.enums;

public enum CategoryType {
    BARDAK("Bardak"),
    TABAK("Tabak"),
    STREC_FILM("Streç Film"),
    SIZDIRMAZ_GRUP("Sızdırmaz Grup"),
    PLASTIK_ELDIVEN("Plastik Eldiven"),
    CATAL("Çatal"),
    KASIK("Kaşık"),
    POSET("Poşet Çeşitleri"),
    KILITLI_POSET("Kilitli Poşet"),
    PECETE_GRUBU("Peçete Çeşitleri");


    private String name;

    private CategoryType(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
