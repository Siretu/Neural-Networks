import sys

name = sys.argv[1]

content = ""
with open(name) as f:
	content = f.read()

content = [x for x in content.split("\n") if x]
content2 = [x.split(",") for x in content]

dates = [x[1] for x in content2]
content3 = content2[::-1]
for (i,x) in enumerate(content3):
		x[1] = dates[i]

content3 = [",".join(x) for x in content3]
content2 = "\n".join(content3)

#print content2
#content2 = "\n".join([x for x in content.split("\n") if x][::-1])

with open(name,"w") as f:
	f.write(content2)
#	pass