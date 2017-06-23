package com.vod.common.util;

import com.vod.common.Constant;

public class UnitConversionUtil
{
    public static final Double inchToCm(Double inch){
        return inch/Constant.INCH_PER_CM;
    }
    public static final Double cmToInch(Double cm){
        return cm*Constant.INCH_PER_CM;
    }
    
    public static final Double ozToKg(Double oz){
        return oz/Constant.KG_PER_OZS;
    }

    public static final Double kgToOz(Double kg){
        return kg*Constant.KG_PER_OZS;
    }
}
