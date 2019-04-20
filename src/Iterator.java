public class Iterator {




    public void first(){
        next();
    }

    public void next() {

    }

    public boolean isDone(){
        return value == -1;
    }


    public int currentValue(){
        return value;
    }

    public void add(int in){
        list.add(in);
    }

    public Iterator getIterator(){
        return new Iterator(this);
    }




}
