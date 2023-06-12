using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace Messanger_B
{
    public static class Ext_HttpListener
    {
        public static System.Net.HttpListener Set_Prefixes_Add(this System.Net.HttpListener _this, string _str = "http://127.0.0.1:8888/connection/", System.Boolean _IsOpen = false)
        {
            _this.Prefixes.Add(_str);
            if (_IsOpen) System.Diagnostics.Process.Start(_str);
            return _this;
        }
        /// <summary>
        /// Начинаем фоном прослушивать входящие подключения
        /// </summary>
        /// <param name="_this"></param>
        /// <returns></returns>
        public static System.Net.HttpListener Set_Start(this System.Net.HttpListener _this)
        { _this.Start(); return _this; }
        public static System.Net.HttpListener Set_Stop(this System.Net.HttpListener _this)
        { _this.Stop(); return _this; }
        public static System.Net.HttpListener Get_ContextAsync(this System.Net.HttpListener _this, System.Action<HttpListenerContext> A)
        { A(_this.GetContextAsync().GetAwaiter().GetResult()); return _this; }
        public static System.Net.HttpListener Get_ContextAsync_WhileTrue(this System.Net.HttpListener _this, System.Action<HttpListenerContext> A)
        { while (true) _this.Get_ContextAsync(A); return _this; }
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
}
