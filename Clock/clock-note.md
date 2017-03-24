# 時針與分針

(1)時針

0 : 90 angle
1 : 60 angle
2 : 30 angle
3 : 0 angle
h : 90 - h * 30 angle

(2)分秒

0 : 90 angle
1 : 84 angle
2 : 78 angle
3 : 72 angle
n : 90 - 6 * n angle

# 角度

(1) 度度量 與 徑度量

度度量 : 0 angle ~ 360 angle

徑度量 : 0 ~ 360 PI

PI = 圓周長 跟 直徑的比 則為 PI 。

(2) 度度量 轉 徑度量

90 angle = PI/2 = 90/180
180 angle = PI = 180/180
60 angle = PI = PI/3
n angle = ( n * PI )/180

公式 : n * Math.PI/180

(3)

小時

```
h -> ( 90 - h * 30) * Math.PI/180 = Math.PI/2 - h * Math.PI/6

```

分鐘

```
n -> ( 90 - 6 * n) * Math.PI/180 = Math.PI/2 - n * Math.PI/30

```

(4) 三角形

三角形 3 邊長 為 l, n, m.

m/l = cos(a) -> m = l * cos(a)

n/l = sin(a) -> n = l * sin(a)

(5) 座標

從座標來看 (x1, y1) 與 (x2, y2) 座標

```
	  (x2, y2)
              .
             /|
            / |
           /  |
          /   |
(x1, y1) /____|
```

y2 < y1
x2 > x1

x2 = x1 + m = x1 + l * cos(a)
y2 = y1 + n = y1 + l * sin(a)



