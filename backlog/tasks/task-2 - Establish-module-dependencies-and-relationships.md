---
id: task-2
title: Establish module dependencies and relationships
status: In Progress
priority: high
milestone: Project Setup Complete
assignee: []
created_date: '2026-05-07 14:38'
updated_date: '2026-05-07 17:02'
labels:
  - dependencies
  - architecture
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Set up proper dependency relationships between app, core, and feature modules

## Acceptance Criteria

- [ ] App module can access all feature modules
- [ ] Core modules are accessible to feature modules
- [ ] No circular dependencies exist
- [ ] Module boundaries are respected

## Implementation Notes

Modified files and changes made:

1. **app/build.gradle.kts** - Added all feature and core module dependencies:
   - Added: implementation(project(":feature:discover"))
   - Added: implementation(project(":feature:search"))
   - Added: implementation(project(":feature:detail"))
   - Added: implementation(project(":feature:favourites"))
   - Added: implementation(project(":core:model"))
   - Added: implementation(project(":core:data"))
   - Added: implementation(project(":core:database"))
   - Added: implementation(project(":core:ui"))

2. **core/data/build.gradle.kts** - Added core:model dependency for DTOs/serialization
   - Added: implementation(project(":core:model"))

3. **core/database/build.gradle.kts** - Added core:model dependency for Room entities
   - Added: implementation(project(":core:model"))

4. **feature/favourites/build.gradle.kts** - Added missing dependencies and Kotlin plugin
   - Added: id("org.jetbrains.kotlin.android") plugin
   - Updated compileSdk from 33 to 34
   - Added: implementation(project(":core:model"))
   - Added: implementation(project(":core:database"))
   - Added lifecycle dependencies for ViewModel support

