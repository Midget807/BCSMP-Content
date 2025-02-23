package com.bcsmp.bcsmp_content.main.domain_expansion.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class DEModMidnightConfig extends MidnightConfig {
    public static final String MAIN = "main";
    public static final String NUMBERS = "numbers";
    public static final String SLIDERS = "sliders";
    public static final String LISTS = "lists";
    public static final String FILES = "files";
    @Comment(category = MAIN, centered = true) public static Comment domainTpEffectText;
    @Entry(category = MAIN, min = 0, max = 600) public static int domainTpEffectFade = 200;
    //@Entry(category = MAIN, name = "Domain Teleport Effect Fade Timer", isSlider = true, min = 0, max = 600) public static int domainTpEffectFadeSlider = 200;
}
