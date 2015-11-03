set title "INSTANCE_B"

set xrange [0:10]
set yrange [0:10]

set xtics 1
set ytics 1

set xlabel "F1"
set ylabel "F2"

set grid

plot '-' title "ALG_A/SETTINGS_A/FUN_1" with lp, \
     '-' title "ALG_A/SETTINGS_A/FUN_2" with lp, \
     '-' title "ALG_A/SETTINGS_B/FUN_1" with lp
        6 3
        6 3
        6 3
        6 3
        6 3
EOF
		1 9
        1 9
        3 8
        3 8
        5 6
		5 7
		8 3        
EOF
        1 9
        2 4
        5 7
        3 8 
        3 9
        10 8
EOF
