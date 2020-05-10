# band-event-manager
Here's what we have so far:
Attendance screen will display users from MainActivity and (temporarily) keep their attendance/emailed data

I spent a LONG time figuring out how to get persistent navigation drawer between activities, and it's not perfect, 
but development on that front for other screens should move faster

# Left to do
We have to add an events screen and a members screen

I rather like the scrolling RecyclerView I have set up for the attendance screen, and it's not too difficult to implement,
but we can also stick with the simpler TextViews if it comes to that.

I need to improve activity flow (easy and intuitive swapping between screens, it's kinda fucky right now). 
I also need to implement attendance data permanence when moving away from attendance screen. My goal is to always send
info back to MainActivity, but we could also write straight to the DB from there (dependent on your expertise there)

Read/write to .csv? 

Sex things up a bit and we should be good to go :)
