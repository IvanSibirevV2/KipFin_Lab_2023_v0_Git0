using System;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Windows;

namespace Messanger_B
{
    /// <summary>
    /// Логика взаимодействия для MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        HttpListener _HttpListener = new HttpListener();
        public MainWindow()
        {
            _HttpListener
             .Set_Prefixes_Add("http://127.0.0.2:8889/connection/", _IsOpen: false)
             .Set_Start()
             ;
            InitializeComponent();
        }
        private void ButtonSendMessage_Click(object sender, RoutedEventArgs e)
        { 
            _HttpListener
                .Get_ContextAsync(a =>
                {
                    System.String _strResponse = TextBoxUser.Text;
                    a.Response.Set_Bytes(_strResponse.Get_Encoding_UTF8_Bytes());
                }
            );
            TextBoxAnswer.Text += "you: " + TextBoxUser.Text + "\n";
            TextBoxUser.Text = String.Empty;
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            _HttpListener.Set_Stop();
        }
        async private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            while (true)
            {
                string asnwerText = await Task.Run(() => new HttpClient().GetStringAsync("http://127.0.0.1:8888/connection/?qweBFYUWEFGUYSDADSDFW").GetAwaiter().GetResult());
                TextBoxAnswer.Text += asnwerText + "\n";
            }
        }
    }
}
