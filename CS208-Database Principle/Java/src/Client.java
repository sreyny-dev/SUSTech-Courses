public class Client {
    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
            DataManipulation dm = new DataFactory().createDataManipulation("database");
            dm.openDatasource();
            //dm.clearDataInTable();
            dm.addUsers();
            dm.addAuthors();
            dm.addPosts();
            dm.addCategories();
            dm.addFollowers();
            dm.addFavourites();
            dm.addShares();
            dm.addLikes();
            dm.addReplies();
            dm.addSecReplies();
            dm.closeDatasource();
            long end = System.currentTimeMillis();
            System.out.println(end-start + "ms");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}

