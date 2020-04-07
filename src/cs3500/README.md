HW6). This assignment was an extension of HW5 and focused on creating a view for
our model. Very little was changed from HW5, however some helper methods
and getter methods were added to our model. An additional field was also
added to ShapeState so that it could hold the ShapeType. This addition
was to make our life easier as well as open up the possibility of changing
a shape's type midway through the animation. Our design for HW6 was split
into 3 different viewers. A BasicView, which uses Java swing to produce
a local frame viewer of the animation. A custom JPanel was used for this,
and was passed an array of shape states. An SVGView, which outputted our
animation as an SVG file. This involved taking the raw data saved to our
model and formatting it in SVG-style. And a TextView, which outputted 
our model as a string to the console window. One nuance to our design
was the overlay of shapes. Originally, the shapes were painted in random
order and wouldn't look like the intended animation (i.e. some shapes 
covered others). To fix this, ShapeState implemented Comparable, which
allowed us to sort the ShapeStates by area, putting the smallest shapes
most in the foreground. Once this was done, all that needed be done was
implementing a builder class that could safely build our views.

HW7). In this assignment, we modified our visual view to
incorporate controls for the animation. This includes the 
animation speed, pause/play mechanics, the ability to loop and
restart, and the option to modify shapes as well as add shapes.
This was done by creating a fourth implementation of the interface
"view". This implementation takes an instance of the visual view
and wraps it with controls, also using the JFrame/swing tools.
Modifying shapes was as simple as having a drop down menu with
all shapes in the animation and populating a text area with
the shape info of the selected shape. This text area was editable
and when the button "save" is pressed, the controller attempts to
overwrite the shape with new data. Pause and play were also simple,
all we did was use a for loop to loop through the ticks. If the
animation was paused, the for loop wouldn't increment. If it was
looped, the for loop would restart. Speed was also very simple,
its just a slider bar that directly sets the model's speed.