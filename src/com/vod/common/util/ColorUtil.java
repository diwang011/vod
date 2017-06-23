package com.vod.common.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ColorUtil
{
    private final static Random RANDOM = new Random();

    private static final Integer getRandomNum()
    {
        return RANDOM.nextInt(150) + 50;
        
    }

    private static final Color getColor()
    {
        Color color = new Color(getRandomNum(), getRandomNum(), getRandomNum());
        return color;
    }

    public static final List<Color> getColor(Integer quantity)
    {
        List<Color> oldColorList = new ArrayList<Color>();
        for (int i = 0; i < quantity; i++)
        {
            for (;;)
            {
                Color color = getColor();
                if (checkColor(oldColorList, color))
                {
                    oldColorList.add(color);
                    break;
                }
            }
        }
        return oldColorList;
    }

    private static final Boolean checkColor(List<Color> oldColorList, Color newColor)
    {
        if (oldColorList == null || oldColorList.size() == 0)
        {
            return true;
        }
        for (Color color : oldColorList)
        {
            Integer index = 0;
            if (newColor.getRed() > color.getRed() - 40 && newColor.getRed() < color.getRed() + 40)
            {
                index++;
            }
            if (newColor.getGreen() > color.getGreen() - 40 && newColor.getGreen() < color.getGreen() + 40)
            {
                index++;
            }
            if (newColor.getBlue() > color.getBlue() - 40 && newColor.getBlue() < color.getBlue() + 40)
            {
                index++;
            }
            if (index > 2)
            {
                return false;
            }
        }
        return true;
    }
}