#@author raindirve

#Räknar med att själv vara i en undermapp till projektets root,
# tillsammans med datafilerna på CSV-format med ändelse .csv
#Kommer lägga resultatfilerna i en undermapp till \historical i
# projektets root.

#Assumption: alphabetically first data file has correct number of lines.
# This is true under our current data with AA.csv having the full 523 lines.


import os, shutil

#hpath = "D:\\Programming\\historicaldata\\"
hpath = "."
hprefix = "..\\historical\\daily\\history"
#hprefix = "..\\historical\\weekly\\history"
hpostfix = ".csv"
firstloop = 1

files = os.listdir(hpath)
files = [x for x in files if x.endswith(".csv")]

hloop = 0
for (file, name) in [(open(x), x) for x in files]:
    lines = file.readlines()
    if firstloop:
        linenum = len(lines)
        firstloop = 0
        print("Number of lines: "+str(linenum) + " in "+name)
    if len(lines) != linenum:
        print(lines[0].split(',')[0] + " has too few lines - not included")
        print(len(lines))
    else:
        file.close()
        shutil.copy2(name, hprefix+str(hloop)+hpostfix)
        hloop += 1
    
    
print(files)

