# GoPass

// // ABOUT // //

GoPass is a password generator. Its function is similar to random password generators, but the generation method is very different. This program generates passwords based on opening moves in a go game.

The method is simple: the Seed Code at the bottom is used to seed a random number generator (RNG). That RNG assigns a value to every intersection on the board. After moves are played, and Make Password pressed, the values of the intersections are used to create a password based on the game record. All of this is done locally on the computer. No information is ever sent outside of the program.

How strong are these passwords? Well, even if a person knew this was how you generated your password AND knew the seed you used, the game of go is so complex that just the first 8 moves have more variation than a 13 character password of random letters and numbers - in fact, it has almost 100 septillion more possible combinations. Yet, for anyone familiar with the game of go, 8 moves can be easily committed to memory. Far more easily than a random string of characters at any rate.

Because go players can remember game moves so well, it allows you to use unique passwords for different websites without having to memorize strange strings of symbols.

Eight moves is the recommended minimum for generating a password. This will produce a 16 character password of upper and lower case letters, numbers, and symbols. Those 16 characters could be hard to remember but, as long as you know what seed you used, you can recreate the password later by entering the moves in the same order again.

This software is released under an MIT License. You can find the source code at https://github.com/chessnerd/GoPass



// // HOW TO USE // //

First, make sure the computer you are using has Java installed.

After downloading the runnable JAR, run it. (On a UNIX system you may need to mark it as executable first, or run "java -jar GoPass.jar" from the command line.)

Enter a seed. This should be something meaningful so that it can be remembered. The seed is obscured so that if someone sees you entering your sequence, they still can't easily use it.

Next, play out some moves. Again, it should be a sequence that can be remembered such as a favorite fuseki or joseki or even a row of stones at some line along the board. 

Note: the board position matters. This program does not account for symmetry. In other words, the upper right star point is a totally different move from the bottom right and so on. This is by design - it makes it harder for someone to guess your password if they know this is how you made it.

Finally, click "Make Password" and the string of characters will be generated. You may now copy this information for use as a password.

If you hit "Make Password" too soon, or without putting in a seed, that's fine! You can change the seed or play additional moves and then click "Make Password" again to overwrite what was written before.

IMPORTANT: THIS PROGRAM DOES NOT SAVE ANY PASSWORDS. Such a thing would be self-defeating. You must remember your seed and move order to recreate the password. If you don't feel confident about this, I'd recommend using an encrypted password manager to improve your password security.

Later on, when you need to enter the password again, just open up GoPass, enter the seed, and replay your moves to recreate the password. 

Because this is written as a stand-alone JAR, any computer with Java can run it (be that computer Windows, Mac, or Linux) meaning you can take it with you on a flash drive or download it again later.



// // HISTORY // // 

2015-03-01  	: Code first uploaded to SourceForge

2015-03-02  	: Graphics bugfixes and performance improvements

2015-06-02  	: Version 1.1 - added features, changed seed conversion/password generation methods - see VERSION.TXT for details

2015-06-05  	: Code first uploaded to GitHub

2015-09-26  	: Version 1.1.1 - performance improvements and code cleanup
