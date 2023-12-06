using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace WebEnterpriseMobileApp
{
    /// <summary>
    /// Initial page for the mobile application
    /// </summary>
    public partial class MainPage : ContentPage
    {
        public IList<BouncerData> Bouncers { get; private set; }

        // SET THE IP OF YOUR HOST MACHINE HERE
        public string ipAddress = "192.168.1.56";

        public MainPage()
        {
            InitializeComponent();

            Bouncers = new List<BouncerData>();
        }

        /// <summary>
        /// Loads bouncer data when pressed
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        async void Button_Clicked(object sender, System.EventArgs e)
        {
            try
            {
                var client = new HttpClient();
                client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));

                var json = await client.GetStringAsync($"http://{ipAddress}:8080/bouncer-fearnall/resources/bouncer/");

                var result = JsonConvert.DeserializeObject<BouncerData[]>(json);

                bouncers.ItemsSource = result;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error: {ex.Message}");
            }
        }
    }
}
