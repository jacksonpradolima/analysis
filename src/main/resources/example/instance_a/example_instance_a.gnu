set title "INSTANCE_A"

set xrange [0:10]
set yrange [0:10]

set xtics 1
set ytics 1

set xlabel "F1"
set ylabel "F2"

set grid

plot '-' title "ALG_A/SETTINGS_A/FUN_1" with lp, \
     '-' title "ALG_A/SETTINGS_A/FUN_2" with lp, \
     '-' title "ALG_A/SETTINGS_A/FUN_3" with lp, \
     '-' title "ALG_A/SETTINGS_B/FUN_1" with lp, \
     '-' title "ALG_A/SETTINGS_B/FUN_2" with lp, \
     '-' title "ALG_B/SETTINGS_A/FUN_1" with lp, \
     '-' title "ALG_B/SETTINGS_A/FUN_2" with lp
        7 9
        8 8
        9 7
EOF
        8 7
        9 4
EOF
        6 9
        7 5
EOF
        6 6
        9 5
EOF
        6 3
        7 1
EOF
        3 9
        4 6
        5 4
        7 2
        9 1
EOF
        2 7
        4 6
        7 4
EOF
