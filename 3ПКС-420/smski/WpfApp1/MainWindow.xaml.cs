﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace WpfApp1
{
    public static class Ext_HttpListener
    {
        public static System.Net.HttpListener Set_Prefixes_Add(this System.Net.HttpListener _this, string _str = "http://127.0.0.1:8888/connection/", System.Boolean _IsOpen = false)
        {
            _this.Prefixes.Add(_str);
            if (_IsOpen) System.Diagnostics.Process.Start(_str);
            return _this;
        }
        public static System.Net.HttpListener Set_Start(this System.Net.HttpListener _this)
        { _this.Start(); return _this; }
        public static System.Net.HttpListener Get_ContextAsync(this System.Net.HttpListener _this, System.Action<HttpListenerContext> A)
        { A(_this.GetContextAsync().GetAwaiter().GetResult()); return _this; }
        public static System.Net.HttpListener Get_ContextAsync_WhileTrue(this System.Net.HttpListener _this, System.Action<HttpListenerContext> A)
        { while (true) _this.Get_ContextAsync(A); }
    }
    public static class Ext_String
    {
        public static byte[] Get_Encoding_UTF8_Bytes(this System.String _this) { return Encoding.UTF8.GetBytes(_this); }
    }
    public static class Ext_HttpListenerResponse
    {
        public static System.Net.HttpListenerResponse Set_Bytes(this System.Net.HttpListenerResponse _this, byte[] _Bytes)
        {
            _this.ContentLength64 = _Bytes.Length;
            Stream output = _this.OutputStream;
            output.WriteAsync(_Bytes, 0, _Bytes.Length);
            output.FlushAsync();
            return _this;
        }
    }

    public partial class MainWindow : Window
    {
        string msg1 = "";
        string msg2 = "";

        public MainWindow()
        {
            InitializeComponent();
            System.Threading.Tasks.Task _TaskA = new Task(() =>
                    new HttpListener()
                   .Set_Prefixes_Add("http://127.0.0.1:8881/connection/", _IsOpen: false)
                   .Set_Start()
                   .Get_ContextAsync_WhileTrue(a => {
                       System.String _strResponse = msg1;
                       msg1 = "";
                       Dispatcher.InvokeAsync(() => this.Messages_Copy.Items.Add(_strResponse));

                       a.Response.Set_Bytes(_strResponse.Get_Encoding_UTF8_Bytes());
                   })
            );
            _TaskA.Start();
            System.Threading.Tasks.Task _TaskB = new Task(() =>
                    new HttpListener()
                   .Set_Prefixes_Add("http://127.0.0.2:8882/connection/", _IsOpen: false)
                   .Set_Start()
                   .Get_ContextAsync_WhileTrue(a => {
                       System.String _strResponse = msg2;
                       msg2 = "";
                       Dispatcher.InvokeAsync(() => this.Messages.Items.Add(_strResponse));

                       a.Response.Set_Bytes(_strResponse.Get_Encoding_UTF8_Bytes());
                   })
            );
            _TaskB.Start();
        }


        private void Send_Message(object sender, RoutedEventArgs e)
        {
            msg1 = SendMessage.Text.Trim();
            SendMessage.Clear();
            if (msg1.Length == 0)
                return;
            new HttpClient().GetStringAsync("http://127.0.0.1:8881/connection/").GetAwaiter().GetResult();
        }
        private void Send_Message_Copy(object sender, RoutedEventArgs e)
        {
            msg2 = SendMessage_Copy.Text.Trim();
            SendMessage_Copy.Clear();
            if (msg2.Length == 0)
                return;
            new HttpClient().GetStringAsync("http://127.0.0.2:8882/connection/").GetAwaiter().GetResult();
        }

    }
}
