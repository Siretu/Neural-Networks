prefix = "weekly"
save = 260

for i in range(23):
	content = ""
	with open("%s/history%d.csv" % (prefix,i)) as f:
		content = f.read()
	with open("%s/history%d.csv" % (prefix,i), "w") as f:
		rows = content.split("\n")
		content2 = "\n".join(rows[len(rows)-save:])
		f.write(content2)