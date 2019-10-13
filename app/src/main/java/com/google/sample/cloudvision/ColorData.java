package com.google.sample.cloudvision;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.max;

public class ColorData {
    private String adjectives[];
    {
        String adj = "forceful,forceful,sweet-sour,sweet-sour,sweet-sour,bitter,bitter,childlike,childlike,childlike,childlike,bright,bright,bright,dazzling,dazzling,dazzling,festive,festive,festive,flamboyant,flamboyant,flamboyant,vivid,vivid,active,active,bold,bold,dynamic,dynamic,dynamic,dynamic and active,dynamic and active,hot,hot,intense,intense,lively,lively,lively,provocative,provocative,provocative,striking,striking,vigorous,vigorous,aromatic,aromatic,aromatic,aromatic,extravagant,extravagant,extravagant,extravagant,rich,rich,rich,substantial,substantial,substantial,robust,robust,robust,wild,wild,wild,charming,charming,innocent,innocent,intimate,intimate,intimate,simple and appealing,simple and appealing,emotional,emotional,emotional,interesting,interesting,interesting,mysterious,polished,polished,chic,chic,chic,tasteful,tasteful,tasteful,eminent,eminent,strong and robust,strong and robust,authoritative,authoritative,agile,agile,speedy,youthful,youthful,youthful,distinguished,distinguished,distinguished,masculine,masculine,modern,modern,precise,precise,sharp,sharp,pretty,pretty,pretty,joyful,amusing,amusing,amusing,cheerful,cheerful,happy,brilliant,brilliant,brilliant,brilliant,fascinating,fascinating,romantic,romantic,romantic,free,free,free,generous,generous,lighthearted,lighthearted,lighthearted,sunny,sunny,sunny,sunny,dry,dry,dry,sedate,sedate,sedate,complex,complex,complex,old-fashioned,old-fashioned,old-fashioned,old-fashioned,placid,placid,subtle and mysterious,subtle and mysterious,urbane,urbane,urbane,showy,showy,showy,showy,glossy,glossy,glossy,mature,mature,mature,mature,ethnic,ethnic,ethnic,untamed,untamed,untamed,untamed,supple,supple,supple,supple,open,open,pastoral,pastoral,pastoral,refined,refined,refined,sleek,sleek,sleek,subtle,subtle,subtle,japanese,japanese,japanese,simple quiet and elegant,simple quiet and elegant,simple quiet and elegant,sober,sober,sober,stylish,stylish,stylish,heavy and deep,heavy and deep,heavy and deep,provincial,provincial,provincial,rustic,rustic,rustic,traditional,traditional,traditional,aristocratic,dapper,dapper,diligent,diligent,practical,practical,quiet and sophisticated,quiet and sophisticated,serious,serious,earnest,earnest,formal,formal,cute,cute,cute,sweet,sweet,sweet,delicious,delicious,delicious,enjoyable,enjoyable,enjoyable,enjoyable,friendly,friendly,merry,merry,merry,tropical,tropical,abundant,abundant,abundant,decorative,decorative,decorative,decorative,gorgeous,gorgeous,gorgeous,luxurious,luxurious,luxurious,luxurious,dreamy,dreamy,dreamy,healthy,healthy,peaceful,peaceful,peaceful,peaceful,peaceful,pleasant,pleasant,pleasant,elegant,elegant,elegant,fashionable,fashionable,fashionable,feminine,feminine,noble,noble,noble,noble and elegant,noble and elegant,majestic,majestic,smart,smart,intellectual,intellectual,domestic,domestic,domestic,domestic,plain,plain,plain,conservative,conservative,solemn,solemn,casual,casual,casual,colorful,colorful,colorful,alluring,alluring,alluring,mellow,mellow,mellow,mellow,agreeable to the touch,agreeable to the touch,agreeable to the touch,agreeable to the touch,amiable,amiable,soft,soft,soft,soft,soft,sweet and dreamy,sweet and dreamy,sweet and dreamy,citrus,gentle and elegant,gentle and elegant,mild,mild,mild,natural,natural,natural,nostalgic,nostalgic,restful,restful,smooth,smooth,smooth,wholesome,wholesome,wholesome,cultured,cultured,cultured,delicate,delicate,delicate,graceful,graceful,pure and elegant,pure and elegant,pure and elegant,tender,tender,tender,modest,modest,classic,classic,elaborate,elaborate,sturdy,sturdy,sturdy,sound,sound,dignified,dignified,precious,precious,clean,clean and fresh,clean and fresh,clear,fresh and young,fresh and young,fresh and young,light,light,neat,neat,pure,pure,pure and simple,pure and simple,refreshing,steady,steady,young,young,cultivated,cultivated,cultivated,grand,grand,fresh,fresh,fresh,gentle,gentle,gentle,gentle,tranquil,tranquil,tranquil,calm,calm,calm,calm,quiet,quiet,quiet,simple,simple,composed,composed";
        adjectives = adj.split(",");
    }
    private String valence[];
    {
        String val = "3.7,3.7,4.133333333,4.133333333,4.133333333,3.63,3.63,5.9,5.9,5.9,5.9,6.84,6.84,6.84,6.5,6.5,6.5,7.14,7.14,7.14,5,5,5,6.41,6.41,6.47,6.47,6.68,6.68,6.1,6.1,6.1,6.285,6.285,5.73,5.73,5.65,5.65,7.12,7.12,7.12,5.47,5.47,5.47,6.383333333,6.383333333,5.75,5.75,6.623333333,6.623333333,6.623333333,6.623333333,6.32,6.32,6.32,6.32,6.83,6.83,6.83,6.14,6.14,6.14,6.1,6.1,6.1,5.94,5.94,5.94,7.05,7.05,6.55,6.55,7.22,7.22,7.22,7.07,7.07,5.11,5.11,5.11,6.78,6.78,6.78,6.05,6.104,6.104,6.47,6.47,6.47,6.89,6.89,6.89,5.58,5.58,6.755,6.755,6.916,6.916,5.95,5.95,5.9,6.89,6.89,6.89,6.62,6.62,6.62,5.79,5.79,6.05,6.05,6.1,6.1,5.24,5.24,7.7,7.7,7.7,8.21,7.6,7.6,7.6,8,8,8.47,7.5,7.5,7.5,7.5,7.37,7.37,7.61,7.61,7.61,8.25,8.25,8.25,7.43,7.43,7.73,7.73,7.73,7.95,7.95,7.95,7.95,4.86,4.86,4.86,4.8,4.8,4.8,4.21,4.21,4.21,4.165,4.165,4.165,4.165,4.84,4.84,4.76,4.76,4.95,4.95,4.95,6.24,6.24,6.24,6.24,5.42,5.42,5.42,5.95,5.95,5.95,5.95,5.36,5.36,5.36,5.89,5.89,5.89,5.89,5.76,5.76,5.76,5.76,6.14,6.14,5.35,5.35,5.35,6.11,6.11,6.11,6.26,6.26,6.26,5.62,5.62,5.62,5.303333333,5.303333333,5.303333333,6.27,6.27,6.27,5.95,5.95,5.95,5.68,5.68,5.68,5.11,5.11,5.11,5.22,5.22,5.22,6,6,6,5.95,5.95,5.95,5.79,6.2175,6.2175,6.14,6.14,5.63,5.63,6.065,6.065,5.88,5.88,5.9,5.9,5.37,5.37,7.56,7.56,7.56,7.77,7.77,7.77,7.38,7.38,7.38,7.63,7.63,7.63,7.63,7.84,7.84,7.2,7.2,7.2,7.05,7.05,7.55,7.55,7.55,7.52,7.52,7.52,7.52,7.57,7.57,7.57,7.58,7.58,7.58,7.58,7.52,7.52,7.52,7.76,7.76,8,8,8,8,8,7.183333333,7.183333333,7.183333333,7,7,7,7.33,7.33,7.33,7.33,7.33,7.16,7.16,7.16,7.036666667,7.036666667,7.14,7.14,7.73,7.73,7.65,7.65,5.05,5.05,5.05,5.05,5.08,5.08,5.08,4.55,4.55,4.66,4.66,6.05,6.05,6.05,7.43,7.43,7.43,6.74,6.74,6.74,6.68,6.68,6.68,6.68,6.77,6.77,6.77,6.77,6.7,6.7,7.13,7.13,7.13,7.13,7.13,7.063333333,7.063333333,7.063333333,6.36,7.21,7.21,5.9,5.9,5.9,6.42,6.42,6.42,6.68,6.68,6.05,6.05,6.42,6.42,6.42,6.9,6.9,6.9,6.15,6.15,6.15,6.38,6.38,6.38,5.73,5.73,6.9,6.9,6.9,6.47,6.47,6.47,6.42,6.42,6.32,6.32,6.38,6.38,6.19,6.19,6.19,6.45,6.45,6.55,6.55,6.84,6.84,7.09,6.633333333,6.633333333,6.14,6.49,6.49,6.49,6.55,6.55,6.95,6.95,6.8,6.8,6.985,6.985,7.05,6.67,6.67,6.31,6.31,6.246666667,6.246666667,6.246666667,6.28,6.28,6.67,6.67,6.67,7.42,7.42,7.42,7.42,7.11,7.11,7.11,6.89,6.89,6.89,6.89,6.47,6.47,6.47,7.17,7.17,6.855,6.855";
        valence = val.split(",");
    }
    private String arousal[];
    {
        String ars = "5.36,5.36,4.696666667,4.696666667,4.696666667,4.64,4.64,5.15,5.15,5.15,5.15,5,5,5,6.36,6.36,6.36,5.67,5.67,5.67,5.52,5.52,5.52,5.26,5.26,5.62,5.62,4.86,4.86,5.43,5.43,5.43,5.525,5.525,4.76,4.76,6.6,6.6,6.1,6.1,6.1,5.9,5.9,5.9,4.95,4.95,5.63,5.63,4.596666667,4.596666667,4.596666667,4.596666667,5.25,5.25,5.25,5.25,6.81,6.81,6.81,5,5,5,5.24,5.24,5.24,5.9,5.9,5.9,5,5,4.4,4.4,6.02,6.02,6.02,5.58,5.58,5.32,5.32,5.32,5.1,5.1,5.1,5.45,5.192,5.192,5.1,5.1,5.1,4.73,4.73,4.73,4.95,4.95,4.91,4.91,4.818,4.818,4.95,4.95,5.1,5.68,5.68,5.68,4.4,4.4,4.4,5.19,5.19,5.4,5.4,5.25,5.25,6,6,5.5,5.5,5.5,5.53,5.18,5.18,5.18,5.76,5.76,6.05,5.95,5.95,5.95,5.95,5.14,5.14,5.12,5.12,5.12,5.38,5.38,5.38,5.7,5.7,5.6675,5.6675,5.6675,5.38,5.38,5.38,5.38,3.8,3.8,3.8,4,4,4,4.6,4.6,4.6,4.175,4.175,4.175,4.175,3.45,3.45,4.6925,4.6925,4.41,4.41,4.41,4.42,4.42,4.42,4.42,4.33,4.33,4.33,3.48,3.48,3.48,3.48,3.55,3.55,3.55,4.3,4.3,4.3,4.3,4.09,4.09,4.09,4.09,4.43,4.43,3.91,3.91,3.91,3.95,3.95,3.95,4.08,4.08,4.08,3.37,3.37,3.37,4.05,4.05,4.05,4.056666667,4.056666667,4.056666667,4.32,4.32,4.32,4.27,4.27,4.27,3.775,3.775,3.775,2.85,2.85,2.85,4.45,4.45,4.45,3.76,3.76,3.76,3.18,4.0625,4.0625,4.33,4.33,3.5,3.5,4.2325,4.2325,4.05,4.05,3.81,3.81,4.48,4.48,5.05,5.05,5.05,4.14,4.14,4.14,4.92,4.92,4.92,4.77,4.77,4.77,4.77,4.27,4.27,4.57,4.57,4.57,4.3,4.3,4.21,4.21,4.21,4.76,4.76,4.76,4.76,4.92,4.92,4.92,4.73,4.73,4.73,4.73,4.9,4.9,4.9,4.19,4.19,4.38,4.38,4.38,4.38,4.38,4.473333333,4.473333333,4.473333333,4.43,4.43,4.43,4.05,4.05,4.05,4.32,4.32,4.14,4.14,4.14,4.19,4.19,4.16,4.16,3.81,3.81,4.53,4.53,2.8,2.8,2.8,2.8,2.68,2.68,2.68,2.65,2.65,2.9,2.9,3,3,3,3.59,3.59,3.59,4.35,4.35,4.35,2.81,2.81,2.81,2.81,4.225,4.225,4.225,4.225,2.85,2.85,3.04,3.04,3.04,3.04,3.04,4.04,4.04,4.04,4.16,3.8,3.8,2.39,2.39,2.39,3.67,3.67,3.67,4.37,4.37,2.58,2.58,2.76,2.76,2.76,4.18,4.18,4.18,3.33,3.33,3.33,3.09,3.09,3.09,4.76,4.76,4.24,4.24,4.24,3.22,3.22,3.22,2.93,2.93,3.62,3.62,4.23,4.23,3.27,3.27,3.27,4.14,4.14,3.48,3.48,3.64,3.64,3.57,2.876666667,2.876666667,2.71,3.22,3.22,3.22,3.52,3.52,4.1,4.1,4.05,4.05,3.38,3.38,4,3,3,4.09,4.09,3.503333333,3.503333333,3.503333333,3.75,3.75,2.35,2.35,2.35,3.17,3.17,3.17,3.17,2.61,2.61,2.61,1.67,1.67,1.67,1.67,1.95,1.95,1.95,2.71,2.71,2.55,2.55";
        arousal = ars.split(",");
    }
    private String color1[];
    {
        String col1 = "22,23,50,62,25,44,143,24,132,22,25,22,24,48,130,34,22,22,22,22,34,34,130,130,130,48,22,22,130,130,22,70,31,23,22,22,142,118,22,22,34,118,130,130,22,130,22,22,42,35,44,32,31,139,31,139,35,35,31,32,65,140,33,142,57,33,22,32,133,133,133,26,37,50,38,65,51,134,29,135,25,138,63,131,113,99,123,125,137,139,57,45,112,119,33,45,143,144,103,115,106,72,48,108,90,116,99,143,111,82,95,101,143,82,142,133,132,25,132,24,131,36,24,24,132,131,131,131,131,126,121,122,134,134,61,37,40,28,28,36,37,40,37,37,37,25,51,51,39,125,136,123,47,126,131,33,44,140,32,41,56,53,41,123,146,113,48,22,120,34,23,24,135,30,47,139,23,31,30,25,43,55,35,31,38,26,134,26,34,48,47,52,53,135,120,125,138,138,121,122,41,125,29,65,126,44,41,54,148,125,53,126,142,52,67,117,141,42,149,52,44,57,44,44,128,42,125,104,57,54,145,33,29,33,45,141,142,44,143,145,143,25,132,132,25,24,25,34,23,43,61,35,132,34,40,36,132,132,24,130,130,35,34,22,23,130,118,126,55,139,139,31,31,47,23,133,86,38,36,46,77,100,37,39,38,39,25,29,123,122,134,74,125,135,135,133,123,126,113,137,123,140,128,77,144,143,143,37,25,25,37,50,63,51,53,44,141,129,24,22,22,48,132,47,23,126,131,139,30,31,44,39,27,27,39,39,39,62,133,27,27,27,25,37,133,49,28,27,38,27,135,42,63,40,30,28,37,35,150,40,26,52,40,52,135,135,138,122,135,111,136,135,120,98,121,26,149,27,41,147,42,140,31,55,141,117,129,45,144,116,33,127,146,109,84,84,74,98,76,61,50,36,86,84,74,98,99,121,109,98,114,84,72,88,114,85,86,47,98,61,60,26,27,39,39,38,51,61,41,50,29,39,45,33,150,149,101,98,151";
        color1 = col1.split(",");
    }
    private String color2[];
    {
        String col2 = "34,142,25,25,61,55,55,50,49,151,49,134,151,22,151,118,34,151,151,151,130,58,34,94,70,82,142,151,94,46,142,46,142,143,54,47,46,46,36,106,115,130,127,72,142,46,106,46,39,37,43,52,55,119,47,47,23,23,47,47,32,54,31,31,31,22,33,47,26,134,74,151,40,37,40,51,64,121,123,28,126,77,124,73,151,150,147,148,123,42,54,53,126,111,145,41,101,147,151,151,151,151,151,61,101,109,101,151,107,149,149,149,101,151,151,151,50,49,49,61,46,73,48,37,122,149,24,124,133,23,132,50,151,151,50,50,50,25,39,50,50,50,38,50,50,38,150,148,38,150,125,135,90,131,32,146,42,54,54,146,53,145,146,112,151,149,48,46,46,82,47,130,136,44,140,23,25,126,55,126,23,31,33,59,111,149,149,150,50,151,51,55,64,148,149,149,125,29,120,123,146,145,51,51,150,148,147,148,53,150,148,147,126,126,148,44,44,54,65,54,42,43,54,117,43,32,119,53,53,138,41,54,32,53,146,145,57,147,148,150,150,49,49,50,50,26,38,42,35,39,132,48,96,46,51,151,46,36,49,84,36,23,52,34,55,47,47,142,31,47,55,30,47,31,47,50,122,86,50,151,51,50,50,50,149,28,26,27,29,124,123,111,111,125,38,38,98,149,150,111,112,113,142,151,110,112,149,38,50,38,28,63,150,62,144,53,146,147,48,48,48,24,84,130,137,30,120,23,31,29,23,26,26,38,51,151,26,26,26,150,151,122,134,133,26,76,135,134,39,28,148,39,50,52,39,140,50,51,39,27,27,151,50,50,122,125,150,148,38,123,137,136,121,121,151,28,122,38,149,65,138,42,128,139,56,43,53,53,53,146,146,41,142,151,151,151,151,58,74,151,151,50,151,151,151,151,110,98,110,88,74,49,48,38,148,115,77,43,85,84,151,123,125,151,86,63,74,50,51,59,149,40,146,53,111,151,151,101,113";
        color2 = col2.split(",");
    }
    private String color3[];
    {
        String col3 = "142,22,61,86,48,144,57,97,97,36,73,72,46,60,46,46,48,58,46,24,46,130,70,46,46,142,94,142,142,142,46,142,35,35,46,44,106,142,106,70,22,94,70,127,96,106,46,115,44,43,40,42,44,47,31,128,47,138,67,44,142,47,143,32,105,66,47,68,98,98,26,74,26,40,51,147,65,125,137,41,59,123,100,119,109,111,145,101,145,57,140,138,142,116,117,142,142,116,108,96,96,108,108,96,144,126,144,114,142,143,142,145,145,142,106,49,108,84,24,120,118,132,25,49,22,127,119,126,126,124,126,98,110,98,73,85,61,50,42,72,52,59,62,61,134,27,53,53,149,149,138,111,138,103,78,32,143,68,143,44,144,143,144,145,102,111,119,94,130,130,120,118,119,47,30,54,138,54,126,66,44,33,56,45,135,74,74,86,96,84,66,64,63,113,109,112,135,135,113,111,144,68,53,125,66,54,149,66,146,113,89,87,100,148,142,143,81,145,145,41,66,55,141,65,68,45,143,142,116,92,57,80,45,57,81,117,44,116,115,117,113,97,84,60,37,37,37,37,30,34,107,84,48,96,87,48,72,58,36,46,83,78,23,131,127,118,31,131,128,127,127,35,32,142,141,98,111,122,96,58,147,75,97,87,76,134,40,136,125,137,137,147,77,101,121,124,113,89,89,143,126,142,92,114,112,107,100,75,63,63,39,148,111,63,44,68,142,144,109,95,107,120,118,127,126,31,129,138,128,127,128,27,25,134,50,51,51,110,122,63,63,86,121,122,121,74,125,123,28,42,137,59,75,51,54,54,63,52,40,26,28,59,63,63,123,123,123,63,149,150,126,137,110,112,109,137,98,121,137,33,105,128,90,141,142,142,93,68,116,142,104,142,126,97,106,97,97,108,151,84,110,72,85,86,108,108,109,38,85,114,71,108,106,114,116,148,141,142,108,98,96,51,123,98,50,148,75,75,53,54,65,53,81,57,147,112,97,143,103";
        color3 = col3.split(",");
    }
    private String basicColorsR[];
    {
        String basR = "231,207,231,233,236,213,211,171,162,172,116,79,238,226,241,242,245,218,218,215,158,167,169,115,85,225,227,225,225,249,233,255,148,139,156,103,75,170,162,169,219,228,209,195,144,109,91,54,19,18,88,155,221,179,141,143,88,30,34,4,6,43,146,209,166,140,122,39,27,31,1,3,0,59,126,194,127,117,130,24,20,18,29,3,6,59,147,203,165,138,133,53,8,16,25,46,92,178,197,224,184,170,151,44,58,40,34,204,175,209,218,235,206,205,160,115,111,88,53,244,175,236,206,180,152,158,126,86,60,38,10";
        basicColorsR = basR.split(",");
    }
    private String basicColorsG[];
    {
        String basG = "47,46,108,163,217,182,142,131,88,36,47,45,113,132,176,178,223,196,196,145,128,100,87,63,55,200,189,228,236,239,227,203,133,117,137,91,63,198,179,199,220,235,213,202,135,116,132,88,166,154,171,196,232,202,188,162,126,98,62,148,134,151,198,234,201,195,165,122,86,56,134,130,147,130,188,222,175,173,154,89,88,83,60,86,113,130,184,215,184,166,154,109,87,76,62,20,104,137,188,218,190,165,150,77,55,57,54,63,92,100,176,219,185,154,147,71,61,60,52,244,92,236,206,180,152,128,126,86,60,38,10";
        basicColorsG = basG.split(",");
    }
    private String basicColorsB[];
    {
        String basB = "39,49,86,144,202,166,110,115,61,48,50,43,25,45,102,103,181,148,148,96,110,67,49,44,43,8,28,15,79,189,143,88,105,65,37,44,45,27,36,35,93,191,165,101,96,73,47,48,50,47,45,113,207,157,90,121,61,50,51,87,84,89,131,211,163,110,123,62,49,45,141,122,159,157,209,242,166,169,145,63,60,65,47,155,148,157,213,232,199,187,153,98,107,84,63,141,163,166,213,230,189,199,139,143,119,103,68,92,87,109,176,224,197,149,131,79,56,50,48,244,87,236,206,180,152,110,126,86,60,38,10";
        basicColorsB = basB.split(",");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Color[] getColorScale(Bitmap img) { // 이미지의 픽셀 색을 분석해 대표색을 뽑아냄
        ArrayList<String> imgColors = new ArrayList<>();
        ArrayList<Integer> imgColorCount = new ArrayList<>();
        for (int x = 0; x < img.getWidth(); x = x + 2) {
            for (int y = 0; y < img.getHeight(); y = y + 2) {
                int color = img.getPixel(x, y);
                int red = Color.red(color);
                int green = Color.green(color);
                int blue = Color.blue(color);
                String hex = String.format("#%02x%02x%02x", red, green, blue);
                if (imgColors.contains(hex)) {
                    int id = imgColors.indexOf(hex);
                    imgColorCount.set(id, imgColorCount.get(id) + 1);
                } else {
                    imgColors.add(hex);
                    imgColorCount.add(1);
                }
            }
        }

        int firstColorID = imgColorCount.indexOf(Collections.max(imgColorCount));
        int firstColor = Color.parseColor(imgColors.get(firstColorID));
        imgColorCount.remove(firstColorID);
        imgColors.remove(firstColorID);
        int secondColorID = imgColorCount.indexOf(Collections.max(imgColorCount));
        int secondColor = Color.parseColor(imgColors.get(secondColorID));
        imgColorCount.remove(secondColorID);
        imgColors.remove(secondColorID);
        int thirdColorID = imgColorCount.indexOf(Collections.max(imgColorCount));
        int thirdColor = Color.parseColor(imgColors.get(thirdColorID));

        Color[] colorScale = new Color[] {Color.valueOf(firstColor), Color.valueOf((secondColor)), Color.valueOf(thirdColor)};
        return colorScale;
    }

    public Color getBasicColor(int n) { // 지정번호를 입력시 Basic Color를 Color 객체로 반환
        Color basic = new Color();
        basic.rgb(Integer.parseInt(basicColorsR[n]), Integer.parseInt(basicColorsG[n]), Integer.parseInt(basicColorsB[n]));
        return basic;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public double getDistance(Color basic, Color c) { // 두 색 사이 거리 구하기
        double dist = (basic.red() - c.red()) * (basic.red() - c.red()) + (basic.green() - c.green()) * (basic.green() - c.green()) + (basic.blue() - c.blue()) * (basic.blue() - c.blue());
        return dist;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Color normalizeColor(Color c) {
        int basicNumber = 0;
        double minDist = 1000000;
        for (int i = 0; i < 131; i++) {
            Color basic = getBasicColor(i);
            double dist = getDistance(basic, c);
            if (dist < minDist) {
                minDist = dist;
                basicNumber = i;
            }
        }
        return getBasicColor(basicNumber);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public double[] getScaleDistance(Color c1, Color c2, Color c3) { // 두 스케일 사이 거리
        double finalDist = 1000000;
        double finalN = 0;
        for (int i = 0; i < 439; i++) {
            ArrayList<Integer> scaleColorNumbers = new ArrayList<>();
            scaleColorNumbers.add(Integer.parseInt(color1[i])-22);
            scaleColorNumbers.add(Integer.parseInt(color2[i])-22);
            scaleColorNumbers.add(Integer.parseInt(color3[i])-22);
            Color[] scaleColors = new Color[] {getBasicColor(scaleColorNumbers.get(0)), getBasicColor(scaleColorNumbers.get(1)), getBasicColor(scaleColorNumbers.get(2))};

            double c1Dist = 1000000;
            int toRemove = 0;
            for (int j = 0; j < 3; j++) {
                double dist = getDistance(scaleColors[j], c1);
                if (dist < c1Dist) {
                    c1Dist = dist;
                    toRemove = j;
                }
            }
            scaleColorNumbers.remove(toRemove);
            scaleColors = new Color[] {getBasicColor(scaleColorNumbers.get(0)), getBasicColor(scaleColorNumbers.get(1))};
            double c2Dist = 1000000;
            toRemove = 0;
            for (int j = 0; j < 2; j++) {
                double dist = getDistance(scaleColors[j], c2);
                if (dist < c2Dist) {
                    c2Dist = dist;
                    toRemove = j;
                }
            }
            scaleColorNumbers.remove(toRemove);
            scaleColors = new Color[] {getBasicColor(scaleColorNumbers.get(0))};
            double c3Dist = getDistance(scaleColors[0], c3);
            if (c1Dist + c2Dist + c3Dist < finalDist) {
                finalDist = c1Dist + c2Dist + c3Dist;
                finalN = i;
            }
        }
        double[] candidate = new double[] {finalDist, finalN};
        return candidate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public double[] getSimilarScale(Color c1, Color c2, Color c3) { // 대표색 3개를 입력시 [Valence, Arousal] 값을 반환
        double[] candidate1 = getScaleDistance(c1, c2, c3);
        double[] candidate2 = getScaleDistance(c1, c3, c2);
        double[] candidate3 = getScaleDistance(c2, c1, c3);
        double[] candidate4 = getScaleDistance(c2, c3, c1);
        double[] candidate5 = getScaleDistance(c3, c1, c2);
        double[] candidate6 = getScaleDistance(c3, c2, c1);

        int finalN = (int)candidate1[1];
        double candidateDist = candidate1[0];
        if (candidate2[0] < candidateDist) {
            candidateDist = candidate2[0];
            finalN = (int)candidate2[1];
        }
        if (candidate3[0] < candidateDist) {
            candidateDist = candidate3[0];
            finalN = (int)candidate3[1];
        }
        if (candidate4[0] < candidateDist) {
            candidateDist = candidate4[0];
            finalN = (int)candidate4[1];
        }
        if (candidate5[0] < candidateDist) {
            candidateDist = candidate5[0];
            finalN = (int)candidate5[1];
        }
        if (candidate6[0] < candidateDist) {
            finalN = (int)candidate6[1];
        }

        double[] valAro = new double[] {Double.parseDouble(valence[finalN]), Double.parseDouble(arousal[finalN]), finalN};

        return valAro;
    }
}
