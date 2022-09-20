databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-10-21-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Removes data in tests')

        rollback {
            dbRollback
        }
    }
}
