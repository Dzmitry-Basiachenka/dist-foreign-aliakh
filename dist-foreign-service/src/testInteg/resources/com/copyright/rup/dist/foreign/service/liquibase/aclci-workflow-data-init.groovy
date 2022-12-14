databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-14-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting data for testAclciWorkflow')

        //TODO: will be implemented in a separate story

        rollback {
            dbRollback
        }
    }
}
