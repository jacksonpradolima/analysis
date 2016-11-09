set title "INSTANCE_B"

set xrange [0:10]
set yrange [0:10]

set xtics 1
set ytics 1

set xlabel "F1"
set ylabel "F2"

set grid

plot '-' title "PFAPPROX" with lp
	1.0 9.0 
	2.0 4.0 
	6.0 3.0  
EOF
