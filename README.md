SkQuery
=======

Changes:
- A lot of bug fixes
- Made it work in versions 1.9-1.12+ (As tested)
- Added tempo option to midi syntax `play midi %string% to %players% [at [tempo] %-number%]`
- Added play midi from web link syntax `play midi from [(web[site]|link)] %string% to %players% [at [tempo] %-number%]`
- Added stop for midi files `stop midi [id] %string%`
- Updated the midi player for the new sounds.
- Removed broken MySQL support (Might add my own methods later) You can use Skellett until then. Example and Example.
- Added file existance condition file `[exist[(s|ance)] [at]] %string% or file %string% exists`
- Removed some files that were not used
- Removed lores, Skript 2.3+ has lores now.
- Removed fireworks, I added them to Vanila Skript.
- Removed SkQuery version expression, not needed.
- Removed BookOf expression, Books are in Vanila Skript now.
- Removed player visibility, it's in Skript.
- Remove recipes, there are other addons with better support that SkQuery doesn't do well at.
- Removed the custom syntax, if you want to do hackable things, try skript-mirror or skript-reflect (Updated skript-mirror)
- Fixed 2.3 color issues
- Fixed Yaml
- Fixed getOnlinePlayers() crash
- Fixed glowing itemtype
- Fixed protocolib crash error
- Fixed the permission manager
- Fixed a bunch of null pointers
- Fixed Time Relative not being able to be reset
- Fixed pop firework effect but removed clientside ability. New syntax:
```
(detonate|pop) %fireworkeffects% at %locations%
```
- Added the ability to do multiple lamba evaluates. New syntax:
```
do [%-number% time[s]] %lambda%
```
- Added former movement location. Uses the getFrom() in the on any movement that never existed.
`([the] (past|former) move[ment] [location]`
```
- Added settable relative option to the time relative syntax.
```
```
(relative|player) time of %player% [with relative %-boolean%]

#or

%player%'s (relative|player) time [with relative %-boolean%]
```
- Fixed the where filter expression not working. (Throws some errors if the predicate contains an unknown expression/value. Looking into fixing that.)
- Changed the any movement syntax from `[on] any movement` to `[on] any move[ment]`
- Removed broken map support.
- Removed the annoying startup message saying this is an unoffical build from Gatt's since it's not really needed.
- Changed this syntax `blocks within %location% (to|and) %location%`
- Added bStats metrics https://bstats.org/plugin/bukkit/SkQuery
- Removed ProtocolLib as a dependency. Meaning these broken syntax don't exist anymore:
```
make %players% see %blocks% as %itemtype% permanently
restore updates to %blocks% for %offlineplayers%
restore all updates
```

But these syntax still work:

```
make %players% see %block% as %itemtype%
make %players% see lines of %block% as %string%, %string%, %string%[ and], %string%
```
https://www.spigotmc.org/resources/skquery.36631/
