import sys
import argparse
import os
from random import randint


def getnumber(n, max, min):
	num = randint(min,max);
	while num == n:
		num = randint(min,max)

	return num;

def main(argv=None):
	parser = argparse.ArgumentParser();
	print ("to use this script you can use -num (num of programs that you want to prove)\n");
	print ("and the program that you want to use (0 = basic, 1= ext1, 2=ext2, 3=ext3 and 4=ext4)\n");
	print ("Default program = 0 and num = 5\n");

	parser.add_argument('-num', default=5, type=int);
	parser.add_argument('-program', default=0, type=int);
	args = parser.parse_args();

	num_cities = 10;
	num_transports = 25;
	num_hotels = num_cities*2;

	dias_min = 0;
	dias_max = 0;


	for i in range(args.num):
		filename = "jocs_prova/" + str(i) + ".pddl";
		if not os.path.exists(os.path.dirname(filename)):
			os.makedirs(os.path.dirname(filename))


		#we are going to write the initial structure
		with open(filename, "w") as f:
			f.write("(define (problem ten-cities) \n");
			f.write("\t(:domain basic-viajes) \n");
			f.write("\t(:objects ")

			#declaring cities
			for j in range(num_cities):
				f.write('c'+ str(j+1) + ' ');
			f.write("- ciudad \n\t\t");

			#declaring hotels
			for j in range(num_hotels):
				f.write('h'+ str(j+1) + '_' +'c'+str((j/2)+1) + ' ');
			f.write("- hotel)\n\t");

			#init
			f.write("(:init ");
			f.write("(visited c1) (in c1)\n\t\t");

			#not visited cities
			for j in range(num_cities-2):
				f.write("(not-visited c"+ str(j+2) +")");
				if j%2 == 0:
					f.write('\n\t\t');

			#localizacion hoteles
			for j in range(num_hotels-2):
				f.write("(localizado c"+ str((j/2)+1) +' h'+ str(j+1) + '_c' + str((j/2)+1) + ')');
				if j%2 == 0:
					f.write('\n\t\t');

			#transportes
			for j in range(num_transports):
				n = getnumber(-1,num_cities,1);
				n2 = getnumber(n,num_cities,1);
				f.write('(transporte c'+str(n)+' c'+str(n2)+')');
				if j%2 == 0:
					f.write('\n\t\t');
				if (args.program >= 3):
					precio_transporte = getnumber(-1,1000,20);
					f.write("(= (precio_transporte c"+ str(n) + 'c' + str(n2) + ') '+ str(precio_transporte)+")");

			#total_ciudades a visitar inicializacion
			f.write("\n\t\t(= (total_ciudades) 0)");

			if (args.program >= 1):
				#dias_total = getnumber(-1,num_cities*2);
				dias_min = getnumber(-1,num_cities//2,1);
				dias_max = getnumber(-1,num_cities//2, dias_min+1);
				f.write("(= (dias_city) 0)\n\t\t");
				f.write("(= (min_total_dias_rec) 0)\n\t\t");
				f.write("(= (min_days_city) "+ str(dias_min)+")\n\t\t");
				f.write("(= (max_days_city) "+ str(dias_max)+")\n\t\t");

			if (args.program == 2 or args.program == 4):
				#not visited cities
				for j in range(num_cities):
					interes = getnumber(-1,3,1);
					f.write("(= (interes c"+ str(j+1) +") "+ str(interes)+")");
					if j%2 == 0:
						f.write('\n\t\t');

				f.write("(= (interes_total) 0)\n\t\t");

			if (args.program >= 3):
				#precios hoteles
				for j in range(num_hotels-2):
					precio_hotel = getnumber(-1,200,10);
					f.write("(= (precio_hotel h"+ str(j+1) + '_c' + str((j/2)+1) + ') '+ str(precio_hotel)+")");
					if j%2 == 0:
						f.write('\n\t\t');
				
				f.write("(= (precio_total) 0)\n\t\t");
			

			#tanquem init
			f.write(")\n\t");


			#comencem goal;
			f.write("(:goal ");
			f.write("(and ");
			num_min_cityes = getnumber(-1,num_cities,(i*10)+1);
			f.write("(= (total_ciudades) "+ str(num_min_cityes) +")");

			if (args.program >= 1):
				dias_total = getnumber(-1, (num_min_cityes*dias_max)-1, (dias_min*num_min_cityes)-1);
				f.write("(>= (min_total_dias_rec) "+ str(dias_total )+") ");
				f.write("(<= (dias_city) (max_days_city)) ");
				f.write("(>= (dias_city) (min_days_city)) \n\t\t");


			if (args.program >= 3):
				precio_min = getnumber(-1,10000,10);
				precio_max = getnumber(-1,50000,precio_min);
				f.write("(<= (precio_total) "+ str(precio_max) +")");
				f.write("(>= (precio_total) "+ str(precio_min) +")");

			#tanquem goal
			f.write("))\n\t");

			#comencem metricas
			if (args.program == 2):
				f.write("(:metric minimize (interes_total)) \n");

			if (args.program == 3):
				f.write("(:metric minimize (precio_total)) \n");

			if (args.program == 4):
				f.write("(:metric minimize (+ (* 75 (interes_total)) (precio_total))) \n");

			f.write(")\n");

		num_cities = num_cities * 2;
		num_transports = num_transports * 2;
		num_hotels = num_cities * 2;

		dias_min = 0;
		dias_max = 0;

	return

if __name__ == "__main__":
	sys.exit(main())