# Simple Temporal Workflow

## Setting
Many applications use Temporal for workflow management and guaranteed eventual completion.
However these applications need state of the workflows stored somewhere (a database) for
idempotency and durability beyond the retention period of Temporal. 

## Problem 
Out of the box, this results in many redundant workflows with similar boilerplate code.
Also, certain attributes in the database entities are only appropriate to be set after
certain progress is made in the workflows. Kotlin allows a rich type system so we want
to expose specific entities with only the attributes that are valid to be set at that
stage of the workflow. Creating this mapping along with redundant similar workflow 
definitions is a lot of boilerplate code.

## Solution and Features
Create linear workflows that progress through steps while saving state into the DB.
Designed to use within Kotlin projects that have Temporal workflows, a database, and Guice.

Features:
- Define Temporal workflows with declarative config and let Temporal manage the progress 
  to successful completion.  
- Tie progress after each Activity into database savepoints for tracking and idempotence.
- Use the Kotlin type system to only expose per Activity the db entity that is appropriate
  to be saved at that stage of the workflow.

Various application code has db entities within the application domain which represent progress
through a linear workflow. E.g. for a report generation and sending report various steps happen
sequentially and are orchestrated by Temporal. The steps can be temporal Activities and the 
state machine lives in Temporal. However, for activity idempotency we need a database too, but 
do not need to duplicate another model of the state within the database. This project leverages
Temporal for handling the workflow, while providing simple wiring of entities to/from the DB.

A common report generation workflow is a sequence of steps, which can easily map to Activites:

1. Check if running in the active region (only continue if in the active region).
2. Validate data is ready for the report to be created.
3. Load data from some data store (a db, a data warehouse, Snowflake, etc) into some report
   specific rows in a datastore.
4. Query over that data to generate an intermediate or final report format (csv, etc).
5. Transmit that report to the destination.
6. Notify completion of report workflow.

All the while, as each of those steps complete maintain a database record that represents this
report activity. That db entity is the durable state representing the expected completion of the
Temporal workflow. Temporal workflow history is limited to a shorter time period than we want to
retain report records, however we expect the workflows to always complete successfully. Therefore
let Temporal manage the "in progress" state tracking, with checkpoints into the db entity for
idempotency. Instead of mapping various database state column values into the Temporal workflow
state, track the `last_completed_temporal_activity` and `last_completed_temporal_activity_at`
attributes in the db. 

These workflow db entities will store some common attributes and may also want to store other 
attributes specific to each workflow. Store extra metadata in a flexible json column. And
based on the state currently at in the workflow, expose specific typed entities into the 
Activities. Motivation: codebase maintainability to ensure entity state values are only set when
the state machine has progressed to the logical point when those attributes are valid to be set.


## Example

TODO


## Building the Project

```bash
./gradlew build
```

## Running Tests

```bash
./gradlew test
```

## Running the Application

```bash
./gradlew run
```

## Project Structure

- `src/main/kotlin/` - Main source code
- `src/test/kotlin/` - Test source code
