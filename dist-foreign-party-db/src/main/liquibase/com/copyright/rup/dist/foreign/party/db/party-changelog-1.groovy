databaseChangeLog {
    property(file: 'database.properties')

    include(file: 'party_application_area_init.groovy', relativeToChangelogFile: true)
    include(file: 'party_permission_init.groovy', relativeToChangelogFile: true)
    include(file: 'party_role_init.groovy', relativeToChangelogFile: true)
    include(file: 'party_role_to_permission_map_init.groovy', relativeToChangelogFile: true)
}
