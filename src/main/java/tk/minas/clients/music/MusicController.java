package tk.minas.clients.music;

public class MusicController {


        private MusicModel model = null;
        private MusicView view  = null;
        /**
         * Constructor
         * @param model The model
         * @param view  The view from which the interaction came
         */
        public MusicController( MusicModel model, MusicView view )
        {
            this.view  = view;
            this.model = model;
        }




    public void goBack() {
            model.goBack();
    }

    public void goNext(){
            model.goNext();
    }

    public void pause() {
            model.pause();
    }

}
