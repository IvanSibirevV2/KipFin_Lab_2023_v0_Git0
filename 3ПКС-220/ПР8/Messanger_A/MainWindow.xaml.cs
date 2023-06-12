using System;
using System.Net.Http;
using System.Net;
using System.Threading.Tasks;
using System.Windows;
using System.ComponentModel;
using System.Windows.Controls;
using System.Windows.Input;

namespace Messanger_A
{
    /// <summary>
    /// Логика взаимодействия для MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        HttpListener _HttpListener = new HttpListener()
            .Set_Prefixes_Add("http://127.0.0.1:8888/connection/", _IsOpen: false)
            .Set_Start();
        private Style TextBlockStyle = (Style)Application.Current.Resources["TextBlockStyle"];
        private Style BorderkStyle = (Style)Application.Current.Resources["BorderkStyle"];
        private Style Gray = (Style)Application.Current.Resources["Gray"];
        public static BackgroundWorker BackgroundWorker = new BackgroundWorker();
        public MainWindow()
        {
            InitializeComponent();
            MaxHeight = System.Windows.SystemParameters.PrimaryScreenHeight - 26;
            BackgroundWorker.DoWork += GetMessegesWork;
            BackgroundWorker.RunWorkerAsync(null);
        }
        
        public void GetMessegesWork(object sender, DoWorkEventArgs e)
        {
            while (true)
            {
                AddTB(new HttpClient().GetStringAsync("http://127.0.0.2:8889/connection/?qweBFYUWEFGUYSDADSDFW").GetAwaiter().GetResult(), HorizontalAlignment.Left, TextAlignment.Left);
            }
        }

        private void AddTB(string Text, HorizontalAlignment ha, TextAlignment ta)
        {
            Application.Current.Dispatcher.Invoke(() =>
            {
                TextBlock tb = new TextBlock
                {
                    Text = Text,
                    Style = TextBlockStyle,
                    TextAlignment = ta,
                    HorizontalAlignment = ha,
                    Margin = new Thickness(5)
                };

                Border border = new Border
                {
                    Style = BorderStyle,
                    Background = Gray,
                    HorizontalAlignment = ha,
                    Child = tb
                };

                MessegesSP.Children.Add(border);
            });
        }
        private void SendClick(object sender, RoutedEventArgs e)
        {
            SendMessege();
        }
        private void TBKeyUp(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter) SendMessege();
        }
        private void SendMessege()
        {
            AddTB(SendText.Text, HorizontalAlignment.Right, TextAlignment.Right);
            _HttpListener
                .Get_ContextAsync(a =>
                {
                    a.Response.Set_Bytes(SendText.Text.Get_Encoding_UTF8_Bytes());
                }
            );
            SendText.Text = string.Empty;   
        }
    }
}
