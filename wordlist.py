def writetofile(msg):
    file = open("res/raw/wordlist","a")
    file.write(msg + "\n")
    file.close()

def alreadyinwordlist(msg):
    x = open("res/raw/wordlist","r")
    contents = x.readlines()
    x.close()
    for i in contents:
        if i.strip() == msg:
            return True
    return False

def main(msg):
    if alreadyinwordlist(msg):
        print "Already in file"
    else:
        print "Not in file"
        writetofile(msg)

while True:
    msg = raw_input("Enter item to add to wordlist: ")
    msg = msg.upper()
    main(msg)
