# band-event-manager

So this app is designed to be used by officers of the Pep Band (and Orchestra?)

Three menus facilitate keeping track of events, members, and each event's attendance log 

There are two .csv files (located somewhere in the APK I guess? We used Android Studio) that can be edited,
and the app can read from the .csv files in order to import event and member data.

There is also an SQLite DB set up for member/event persistence when the app is closed (although in hindsight
I don't think it actually saves attendance information. That would require additional columns since each event
stores a list of all members and their attendance. TO-DO?)

Check it out, download it, use it all you want! ¯\\\_(ツ)\_/¯
