set title "INSTANCE_A"

set xrange [0:10]
set yrange [0:10]

set xtics 1
set ytics 1

set xlabel "F1"
set ylabel "F2"

set grid

plot '-' title "ALG_A/SETTINGS_A/FUNALL" with lp, \
     '-' title "ALG_A/SETTINGS_B/FUNALL" with lp, \
	 '-' title "ALG_B/SETTINGS_A/FUNALL" with lp
        6 9
        7 5
        9 4
EOF
        6 3 
        7 1 
EOF
        2 7 
        4 6 
        5 4 
        7 2 
        9 1 
EOF
