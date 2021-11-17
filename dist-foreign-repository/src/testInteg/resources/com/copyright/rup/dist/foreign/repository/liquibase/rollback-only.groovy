databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-11-16-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Change set to remove rows added by calling repository methods')

        rollback {
            dbRollback
        }
    }
}
