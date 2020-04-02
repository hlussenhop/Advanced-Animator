This assignment was an extension of HW5 and focused on creating a view for
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