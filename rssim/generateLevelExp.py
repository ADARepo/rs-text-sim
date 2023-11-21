import math

exp = 83
diff = 0

with open('levels.txt', 'w') as f:
    f.write('1=' + '0' + '\n')
    f.write('2=' + str(int(exp)) + '\n')

    diff = exp

    for i in range(3, 101):
        nextLevelExp = exp + diff + (diff / 10)
        diff = diff + (math.ceil(diff / 10))
        exp = nextLevelExp

        f.write(str(i) + '=' + str(int(exp)))

        if i != 100:
            f.write("\n")

    f.close()