databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-01-28-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for NtsUsageRepositoryIntegrationTest")
    }
}
