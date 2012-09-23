==========
Assistdone
==========

Assistdone is the outcome of my final project. In it, I tried to
implement Domain Driven Design (DDD), Command-Query Responsibility
Segregation (CQRS), and Behavior-Driven Development (BDD). You can
say that I tried to kill three birds with one stone.

Of course, to implement those all, I needed a domain. For that, I
chose Getting Things Done (GTD).


The Components
--------------

There are five core components, each has its respective directory.
To get better view, here's the simple relationship diagram for the
final product:

::

                            +----------------------+
                            | UI/Application       |
                            |                      |
                            |  +----------------+  |
                            |  |AssistdoneMobile|  |
                            |  +----------------+  |
                            +----------------------+
                                 /           \
                                /             \
                               #               #
    +--------------------------------+    +--------------------------------+
    | Domain Model                   |    | Infrastructure                 |
    |                                |    |                                |
    |  +--------+    +------------+  |    |  +-------+     +------------+  |
    |  |GtdStuff|    |GtdInventory|  |    |  |Badulik|     |Time library|  |
    |  +--------+    +------------+  |    |  +-------+     +------------+  |
    |          \         /           |---#|                                |
    |           #       #            |    |                                |
    |        +-------------+         |    |  +-------+     +-----+         |
    |        |GtdDefinition|         |    |  |Java ME|     |JSON |         |
    |        +-------------+         |    |  +-------+     +-----+         |
    +--------------------------------+    +--------------------------------+


GtdDefinition (applies DDD)
'''''''''''''''''''''''''''
This component models basic representation for GTD.


GtdStuff (applies DDD and Command (the 'C' on CQRS))
''''''''''''''''''''''''''''''''''''''''''''''''''''
This component models the GTD processing and its Stuff lifetime.


GtdInventory (applies DDD and Query (the 'Q' on CQRS))
''''''''''''''''''''''''''''''''''''''''''''''''''''''
This component models the GTD inventory. Because GTD inventory
has the same concept as Query, it also contains the Query logic.


FeatureSpec (applies DDD and BDD)
'''''''''''''''''''''''''''''''''
This component doesn't appear on the above diagram because it's
not needed in the final product. However, this component plays
important role in validating the Domain Model representation of
`GtdDefinition`, `GtdStuff`, and `GtdInventory`.


AssistdoneMobile
''''''''''''''''
This component acts the "glue" for everything.
