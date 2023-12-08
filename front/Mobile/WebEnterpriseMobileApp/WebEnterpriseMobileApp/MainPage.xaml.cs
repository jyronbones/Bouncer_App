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
        public string ipAddress = "192.168.1.48";

        // SET THE USERNAME FOR THE REQUEST HERE
        public string userName = "admin";

        // SET THE PASSWORD HERE
        public string password = "123";

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
                string credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes($"{userName}:{password}"));

                client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));
                client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Basic", credentials);

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
