databaseChangeLog {
    property(file: 'database.properties')
    
    include(file: 'repository-integ-test-changelog.groovy', relativeToChangelogFile: true)
}
