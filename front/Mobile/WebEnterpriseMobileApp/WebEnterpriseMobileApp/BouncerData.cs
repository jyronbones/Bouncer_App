using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace WebEnterpriseMobileApp
{
    /// <summary>
    /// Class used to model a bouncer object from the netbeans project
    /// </summary>
    public class BouncerData
    {
        /// <summary>
        /// The bouncers Id
        /// </summary>
        [JsonProperty("id")]
        public long Id { get; set; }
        /// <summary>
        /// The bouncers x position
        /// </summary>
        [JsonProperty("x")]
        public int X { get; set; }
        /// <summary>
        /// The bouncers y position
        /// </summary>
        [JsonProperty("y")]
        public int Y { get; set; }
        /// <summary>
        /// The bouncers y velocity
        /// </summary>
        [JsonProperty("YVelocity")]
        public int YVelocity { get; set; }
    }
}
