set title "INSTANCE_A"

set xrange [0:10]
set yrange [0:10]

set xtics 1
set ytics 1

set xlabel "F1"
set ylabel "F2"

set grid

plot '-' title "PFAPPROX" with lp
        2 7
        4 6 
        5 4 
        6 3 
        7 1 
EOF
