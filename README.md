# bookworm-solver
Video: https://github.com/mrincodi/bookworm-solver
https://www.youtube.com/watch?v=R09e4ltUWew

Still very much incomplete!!

There is a game called Bookworm (https://en.wikipedia.org/wiki/Bookworm_(video_game), https://kbhgames.com/game/bookworm-online). The game is about finding out the longest contiguous words in English on a board full of letters. My solution reads the screen, finds out the longest word and clicks the screen in the right places as to form that word.
 
The solution follows this sequence:
- Pass dictionary text file to trie.
- Locate “Bookworm” logo and letters on the user screen.
- Read characters from the screen, and interpret them into characters.
- Send characters to the solver.
- Solver returns the coordinates of one of the longest words from the trie.
- Coordinates are translated into locations in the screen.
- Screen is clicked in those locations.
 
Tools used:
Java, Sikuli. I learned Sikuli while doing this program.
 
Challenges:
- Learning Sikuli!
- Creating my primitive OCR (Character recognition) because the one in Sikuli was not detecting the letters. This involved "taking pictures" of all the individual tiles (and a lot of pixel cropping madness afterwards).
- Creating the trie from the dictionary (from previous Hackathon).
- Finding the longest word(s) in the trie, from the board (from previous Hackathon).
- IntelliJ sucks! I was going nuts with its library configuration. Long live Eclipse! :)
 
Potential enhancements:
- Speed up the reading! It's still taking too long (find a decent OCR?).
- Continuous playing: don't only solve the first screen. Keep playing word after word and level after level.
- Intelligent playing:, which means, among other things:
   -- Not all the board tiles nee  to be read after a word is cleared. Only the missing tiles. This would speed up reading.
   -- AI could be used strategically so smaller words are cleared strategically, so a very long or valuable word is formed.
- Sometimes the word found in the dictionary ins not valid for Bookworm. For this cases, an intelligent "retry" needs to be coded.
- Support of "fire", “diamond” and “emerald” tiles. Sometimes, letters change colors, as bonus points. These are not recognized by my OCR.
- Bring the word with the highest score, not necessarily the longest word.
- Support for the combination “Qu” (a single letter in the game, still not supported by my solution).
 
 

